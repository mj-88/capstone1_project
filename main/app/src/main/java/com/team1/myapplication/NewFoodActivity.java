package com.team1.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.loader.content.CursorLoader;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;

import android.os.Bundle;
import android.os.Environment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.Manifest;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class NewFoodActivity extends AppCompatActivity implements View.OnClickListener {


    ImageView imageView;
    int imgFrom;
    Button btnCamera, btnGallery,btnUpload;
    EditText mealName;
    String stremailId;

    final int CAMERA = 100; // 카메라 선택시 인텐트 값
    final int GALLERY = 101; // 갤러리 선택 시 인텐트 값
    Intent intent;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat imageDate = new SimpleDateFormat("yyyyMMdd_HHmmss");

    ProgressDialog mProgressDialog;
    String imagePath = "";
    String TAG = "@@TAG@@";

    File imageFile = null; // 카메라 선택 시 새로 생성하는 파일 객체
    Uri imageUri = null;

    FirebaseStorage storage = FirebaseStorage.getInstance(); // 파이어베이스 저장소 객체
    StorageReference reference = null; // 저장소 레퍼런스 객체 : storage 를 사용해 저장 위치를 설정


    private DatabaseReference mDatabase;


     FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

String ImageRealName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_food);


        imageView = findViewById(R.id.imageButton);
        btnCamera = findViewById(R.id.photoButton);
        btnGallery = findViewById(R.id.galleryButton);
        btnUpload = findViewById(R.id.saveButton);
        mealName = (EditText) findViewById(R.id.editTextFood);

        btnCamera.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        btnUpload.setOnClickListener(this);


        boolean hasCamPerm = checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean hasWritePerm = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        if (!hasCamPerm || !hasWritePerm)  // 권한 없을 시  권한설정 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        FirebaseApp.initializeApp(/*context=*/ this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance());
    }



    @SuppressLint({"NonConstantResourceId", "QueryPermissionsNeeded"})
    @Override
    public void onClick(View view) {

        String strMealName = mealName.getText().toString();


        switch (view.getId()) {
            case R.id.photoButton:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    try {
                        imageFile = createImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (imageFile != null) {
                        imageUri = FileProvider.getUriForFile(getApplicationContext(),
                                "com.team1.myapplication.fileprovider",
                                imageFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                        launcher.launch(intent);
                        startActivityForResult(intent, CAMERA);

                    }
                }
                break;
            case R.id.galleryButton: // 갤러리 선택 시
                intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY);

                break;
            case R.id.saveButton:
                if (imagePath.length() > 0 && imgFrom >= 100) {

                    //food DB에 데이터 저장
                    if(ImageRealName != null  ) {

                        uploadImg();

                        Date nowDate = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                        String strSaveDate = simpleDateFormat.format(nowDate);


                        FirebaseUser get_Auth_value = FirebaseAuth.getInstance().getCurrentUser();
                        String stremailId = get_Auth_value.getEmail();

                        mDatabase = ref.child("food");


                        MealUpload mealUpload = new MealUpload(strSaveDate , stremailId, ImageRealName, strMealName);
                        mDatabase.push().setValue(mealUpload);
                        Toast.makeText(NewFoodActivity.this, "loading...", Toast.LENGTH_LONG).show();
                        finish();

                    }else {
                                    Toast.makeText(NewFoodActivity.this, "식단명을 입력하세요.", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) { // 결과가 있을 경우
//            갤러리를 선택한 경우 인텐트를 활용해 이미지 정보 가져오기
            if (requestCode == GALLERY) { // 갤러리 선택한 경우
                imageUri = data.getData(); // 이미지 Uri 정보
                imagePath = data.getDataString(); // 이미지 위치 경로 정보
            }
//            저장한 파일 경로를 이미지 라이브러리인 Glide 사용하여 이미지 뷰에 세팅하기
            if (imagePath.length() > 0) {
                GlideApp.with(this)
                        .load(imagePath)
                        .into(imageView);
                imgFrom = requestCode; // 사진을 가져온 곳이 카메라일 경우 CAMERA(100), 갤러리일 경우 GALLERY(101)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    File createImageFile() throws IOException {
        String timeStamp = imageDate.format(new Date()); // "yyyyMMdd_HHmmss" timeStamp
        String fileName = "IMAGE_" + timeStamp; // 이미지 파일 명
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = File.createTempFile(fileName,
                ".jpg",
                storageDir); // 이미지 파일 생성
        imagePath = file.getAbsolutePath(); // 파일 절대경로 저장
        return file;
    }

    void uploadImg() {
//        firebase storage 에 이미지 업로드하는 method
        UploadTask uploadTask = null; // 파일 업로드하는 객체
        switch (imgFrom) {
            case GALLERY:
                /*갤러리 선택 시 새로운 파일명 생성 후 reference 에 경로 세팅,
                 * uploadTask 에서 onActivityResult()에서 받은 인텐트의 데이터(Uri)를 업로드하기로 설정*/
                String timeStamp = imageDate.format(new Date()); // 중복 파일명을 막기 위한 시간스탬프
                String imageFileName = "IMAGE_" + timeStamp + "_.png"; // 파일명
                reference = storage.getReference().child("image").child(imageFileName); // 이미지 파일 경로 지정 (/item/imageFileName)
                uploadTask = reference.putFile(imageUri); // 업로드할 파일과 업로드할 위치 설정
                ImageRealName = imageFileName;
                break;
            case CAMERA:
                /*카메라 선택 시 생성했던 이미지파일명으로 reference 에 경로 세팅,
                 * uploadTask 에서 생성한 이미지파일을 업로드하기로 설정*/
                reference = storage.getReference().child("image").child(imageFile.getName()); // imageFile.toString()을 할 경우 해당 파일의 경로 자체가 불러와짐
                uploadTask = reference.putFile(Uri.fromFile(imageFile)); // 업로드할 파일과 업로드할 위치 설정

                ImageRealName = imageFile.getName();
                break;
        }

//        파일 업로드 시작
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//            업로드 성공 시 동작
//                hideProgressDialog();

                Log.d(TAG, "onSuccess: upload");
                downloadUri(); // 업로드 성공 시 업로드한 파일 Uri 다운받기
                Toast.makeText(NewFoodActivity.this," 식단 저장 완료 !! ",Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                업로드 실패 시 동작
//                hideProgressDialog();
                Log.d(TAG, "onFailure: upload");
            }
        });
    }

    void downloadUri() {
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }




    public void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public String getPath(Uri uri){
        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri,proj,null,null,null);
        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();
        return cursor.getString(index);
    }


}


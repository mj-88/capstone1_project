package com.team1.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team1.myapplication.ml.Model;

import java.util.ArrayList;

public class LastMealActivity extends AppCompatActivity {

    private ImageView imageView;
    private Uri imageUri;
    private ArrayList<Model> list;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("image");



    private RecyclerView recyclerView;
    private RecyclerView.Adapter  adapter;
    private RecyclerView.LayoutManager layoutManger;
    private ArrayList<Food> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_meal);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManger = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManger);
        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference("food");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 DB에서 데이터 받아옴

                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Food food = snapshot.getValue(Food.class);
                    arrayList.add(food);
                }

                adapter.notifyDataSetChanged();

                adapter = new CustomAdapter(arrayList,recyclerView.getContext());
                recyclerView.setAdapter(adapter);
                Log.d("제발  안  ", this.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.e("LastMealActivity", String.valueOf(error.toException()));


            }
        });


//        adapter = new CustomAdapter(arrayList,this);


        adapter = new CustomAdapter(arrayList,recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        Log.d("제발    ! 밖", this.toString());


    }




//        list = new ArrayList<>();
//
//        adapter = new ImageAdapter(LastMealActivity.this ,list);
//
//        recyclerView.setAdapter(adapter);
//
//        root.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//
//                    Model model = dataSnapshot.getValue(Model.class);
//                    list.add(model);
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//                Intent galleryIntent = new Intent();
//                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
//                galleryIntent.setType("image/*");
//                activityResult.launch(galleryIntent);
//    }



//    ActivityResultLauncher<Intent> activityResult = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//
//                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
//
//                        imageUri = result.getData().getData();
//
//                        imageView.setImageURI(imageUri);
//                    }
//                }
//            });


}
package com.team1.myapplication;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private EditText mEtEmail, mEtPasswordCheck,mEtPassword,mEtWeight, mEtHeight,mEtNickName, mEtGender;
    private Button mBtnRegister;

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("MyApplication");

        mEtEmail = findViewById(R.id.et_email);
        mEtPassword = findViewById(R.id.et_password);
        mBtnRegister = findViewById(R.id.btn_register);
        mEtPasswordCheck = findViewById(R.id.et_passwordCheck);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입
                String strEmail = mEtEmail.getText().toString(); //입력받은 이메일을 가져옴
                String strPassWord = mEtPassword.getText().toString(); //입력받은 비밀번호를 가져옴
                String pwdcheck = mEtPasswordCheck.getText().toString().trim(); //비밀번호를 맞게 입력했는지 확인

//                String strWeight= mEtWeight.getText().toString();
//                String strHeight = mEtHeight.getText().toString();
//                String strNickName = mEtNickName.getText().toString();

                //라디오 그룹 설정
                radioGroup = (RadioGroup) findViewById(R.id.rg_gender);
                radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

                if(strPassWord.equals(pwdcheck)) {
                    mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPassWord).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                                UserAccount account = new UserAccount();
                                account.setPassword(strPassWord);
                                account.setEmailId(firebaseUser.getEmail());
                                account.setIdToken(firebaseUser.getUid());

                                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                                Toast.makeText(RegisterActivity.this, "회원가입 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }



            RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) { } };

        });

    }
}
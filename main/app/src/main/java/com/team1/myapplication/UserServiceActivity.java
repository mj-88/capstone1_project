package com.team1.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserServiceActivity extends AppCompatActivity {


    private EditText EtTitle, EtContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_service);


        EtTitle = findViewById(R.id.editTextTitle);
        EtContent = findViewById(R.id.editTextContent);


        Button btnSend =findViewById(R.id.buttonSend);
        btnSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

//                String title = EtTitle.getText().toString(); //입력받은 이메일을 가져옴
//                String content= EtContent.getText().toString(); //입력받은 비밀번호를 가져옴
//
//                Intent mail_intent = new Intent(Intent.ACTION_SEND);
//                mail_intent.setType("*/*");
//
//                mail_intent.putExtra(Intent.EXTRA_EMAIL, "choiminji_1@naver.com"); // 받는 사람 이메일
//                mail_intent.putExtra(Intent.EXTRA_SUBJECT, title); // 메일 제목
//                mail_intent.putExtra(Intent.EXTRA_TEXT, content); // 메일 내용
//                startActivity(mail_intent);

                Intent intent = new Intent(UserServiceActivity.this, ProfileActivity.class);
                startActivity(intent);

            }
        });


    }





}
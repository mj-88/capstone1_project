package com.team1.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class ProfileDetailActivity extends AppCompatActivity {

    private FirebaseUser get_Auth_value;
    private TextView email_view, nickname_view, gender_view;

    private EditText height_view, weight_view;

    private String change_we,change_he;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        Button btn_save = findViewById(R.id.profile_save);

        // FirebaseAuth에서 현재 로그인한 계정의 이메일 가져오기 + 화면에 출력
        get_Auth_value = FirebaseAuth.getInstance().getCurrentUser();
        String email_get = get_Auth_value.getEmail();
        email_view = findViewById(R.id.tv_email);
        email_view.setText(email_get);

        // 이메일 외 나머지 값을 가져오기 위한 현재 계정의 uId 가져오기
        String uID = get_Auth_value.getUid();


        nickname_view = (TextView) findViewById(R.id.tv_nickname);
        gender_view = (TextView) findViewById(R.id.tv_gender);

        weight_view = (EditText) findViewById(R.id.edit_weight_view);
        height_view = (EditText) findViewById(R.id.edit_height_view);


        //FirebaseDatabase에서 닉네임, 성별, 키, 몸무게 가져오기
        FirebaseDatabase.getInstance().getReference().child("UserAccount/" + uID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, String> map_Value;
                map_Value = new HashMap<String, String>();

                map_Value.putAll((Map<? extends String, ? extends String>) dataSnapshot.getValue());

                String getNickName = (String) map_Value.get("user_nickname");
                String getGender = (String) map_Value.get("gender");
                String getWeight = (String) map_Value.get("weight");
                String getHeight = (String) map_Value.get("height");

                nickname_view.setText(getNickName);
                gender_view.setText(getGender);
                weight_view.setText(getWeight);
                height_view.setText(getHeight);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        weight_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                change_we=weight_view.getText().toString();
            }
        });

        height_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                change_he=height_view.getText().toString();
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("UserAccount/"+uID).child("weight").setValue(change_we);
                FirebaseDatabase.getInstance().getReference("UserAccount/"+uID).child("height").setValue(change_he);
                Toast.makeText(ProfileDetailActivity.this, "저장 완료!", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
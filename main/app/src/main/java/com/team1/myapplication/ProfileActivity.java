package com.team1.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.BinderThread;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {


   private FirebaseAuth user_logout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        user_logout = FirebaseAuth.getInstance();

        Button button_logout = (Button) findViewById(R.id.logout);
        Button button_secession = (Button) findViewById(R.id.secession);
        Button button_detail =(Button) findViewById(R.id.profile_detail);
        Button button_userservice =(Button) findViewById(R.id.button);

        button_secession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Intent intent1 = new Intent(getApplicationContext(), SecessionActivity.class);
                startActivity(intent1);
            }
        });




        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
              user_logout.signOut();
                Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent2);
            }
        });

        button_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Intent intent3 = new Intent(getApplicationContext(), ProfileDetailActivity.class);
                startActivity(intent3);
            }
        });

        button_userservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Intent intent4 = new Intent(getApplicationContext(), UserServiceActivity.class);
                startActivity(intent4);
            }
        });



    }
}
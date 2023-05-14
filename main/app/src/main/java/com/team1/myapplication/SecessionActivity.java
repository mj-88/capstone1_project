package com.team1.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class SecessionActivity extends AppCompatActivity {


    private FirebaseUser user;

    private TextView email_View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secession);

        user =FirebaseAuth.getInstance().getCurrentUser();

        String email = user.getEmail();
        email_View = (TextView)findViewById(R.id.textView5);
        email_View.setText(email);




        Button button_secession = (Button)findViewById(R.id.button3);
        button_secession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                secession();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void secession(){
        user.delete().
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete (@NonNull Task< Void > task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                        }
                    }
                });
    }


}
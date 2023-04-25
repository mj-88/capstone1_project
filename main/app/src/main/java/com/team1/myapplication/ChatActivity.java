package com.team1.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

        private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ChatData> chatList;
    private String str_user_name;
    private String str_room_name;
    private EditText EditText_chat;
    private Button Button_send;

    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Button_send=findViewById(R.id.Buttton_send);
        EditText_chat=findViewById(R.id.EditText_chat);

        str_room_name=getIntent().getExtras().get("room_name").toString();
        str_user_name=getIntent().getExtras().get("user_name").toString();

        myRef= FirebaseDatabase.getInstance().getReference().getRoot().child("chat").child(str_room_name);

        setTitle(str_room_name);

        Button_send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String msg=EditText_chat.getText().toString();
                EditText_chat.setText("");


                if(msg!=null){
                    Object timestamp = ServerValue.TIMESTAMP; //!!
                    ChatData chat=new ChatData();
                    chat.setNickname(str_user_name);
                    chat.setMsg(msg);
                    chat.setSendTime(timestamp);
                    myRef.push().setValue(chat);
                }
            }
        });

        mRecyclerView=findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        chatList=new ArrayList<>();
        mAdapter=new ChatAdapter(chatList, str_user_name);

        mRecyclerView.setAdapter(mAdapter);


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot datasnapshot, String s) {

                ChatData chat = datasnapshot.getValue(ChatData.class);
                ((ChatAdapter) mAdapter).addChat(chat);
            }

            @Override
            public void onChildChanged(DataSnapshot datasnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot datasnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot datasnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

package com.team1.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class chatRoomList extends AppCompatActivity {

    private ListView listView;
    private Button btn_create;

    private ArrayAdapter<String> arrayAdapter;

    private ArrayList<String> arr_roomList=new ArrayList<>();
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().getRoot();

    private String str_name;
    private String str_room;

    private SearchView mSearchView;

//    Map<String, Object> map = new HashMap<String, Object>();
    private String uid;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_list);

        setTitle("채팅방 목록");

        //회원정보에서 닉네임 가져오기
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Username=database.getReference("UserAccount").child(uid).child("user_nickname");

        Username.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                str_name=snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        listView=(ListView) findViewById(R.id.list);
        btn_create=(Button) findViewById(R.id.btn_create);

        //채팅방 리스트 보이기
        arrayAdapter=new ArrayAdapter<String>(this, R.layout.roomlist_layout, arr_roomList);
        listView.setAdapter(arrayAdapter);

        //채팅방 이름 적어 채팅방 생성
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText et_inDialog=new EditText(chatRoomList.this);

                final AlertDialog.Builder builder =new AlertDialog.Builder(chatRoomList.this);
                builder.setTitle("채팅방 이름 입력");
                builder.setView(et_inDialog);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        str_room=et_inDialog.getText().toString();

                        if(str_room!=null){
                            arr_roomList.add(str_room); //채팅방 목록 입력시 바로 파이어베이스에 추가X, 채팅 입력 시 추가됨
                            arrayAdapter.notifyDataSetChanged();
                        }
                        else{dialogInterface.dismiss();}
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();

            }
        });

        reference.child("chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                Set<String> set=new HashSet<String>();
                Iterator i =dataSnapshot.getChildren().iterator();


                while (i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());
                }

                arr_roomList.clear();
                arr_roomList.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }
            @Override public void onCancelled(DatabaseError databaseError){

            }

        });

        mSearchView = findViewById(R.id.searchView);

        //필터
        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchView.setIconified(false); //검색창 모든 영역 선택해도 검색가능
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                listView.setAdapter(arrayAdapter);
                return false;
            }
        });


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<String> filterRoom = new ArrayList<>();
                ArrayAdapter<String>adapter = new ArrayAdapter<String>(chatRoomList.this, R.layout.roomlist_layout, filterRoom);

                if (newText.length() == 0) {
                    filterRoom.addAll(arr_roomList);
                    listView.setAdapter(adapter);
                }
                else{
                    for(int i=0;i<arr_roomList.size();i++){
                        String room=arr_roomList.get(i);
                        if (room.toLowerCase().contains(newText.toLowerCase())){
                            filterRoom.add(room);
                        }
                    }
                    listView.setAdapter(adapter);


                }
                return false;
            }

        });

        //리스트뷰의 채팅방 클릭 시 반응
        //채팅방 이름, 유저 이름 전달
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                Intent intent =new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("room_name", ((TextView)view).getText().toString());
                intent.putExtra("user_name", str_name);
                startActivity(intent);

                Toast toast = Toast.makeText(getApplicationContext(), str_name+"채팅방에 입장했습니다.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
            }
        });
    }
}
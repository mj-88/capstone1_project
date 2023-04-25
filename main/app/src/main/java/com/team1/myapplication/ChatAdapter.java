package com.team1.myapplication;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private final List<ChatData>mDataset;
    private final String myNickname;

    private final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy.MM.dd HH:mm");


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView TextView_nickname;
        public TextView TextView_msg;
        public TextView TextView_time;
        public View rootView;

        public LinearLayout linearLayout_destination;
        public LinearLayout linearLayout_main;


        public MyViewHolder(View v){
            super(v);
            TextView_nickname = (TextView)v.findViewById(R.id.TextView_nickname);
            TextView_msg = (TextView)v.findViewById(R.id.TextView_msg);
            TextView_time=(TextView) v.findViewById(R.id.TextView_time);

            linearLayout_destination = (LinearLayout)v.findViewById(R.id.messageItem_linearlayout_destination);
            linearLayout_main = (LinearLayout)v.findViewById(R.id.messageItem_linearlayout_main);

            rootView=v;

            v.setClickable(true);
            v.setEnabled(true);

        }
    }

    public ChatAdapter(List<ChatData>myDataset, String myNickname){
        mDataset=myDataset;
        this.myNickname=myNickname;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LinearLayout v=(LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat, parent, false);

        return new MyViewHolder(v);
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){

        ChatData chat=mDataset.get(position);

        holder.TextView_nickname.setText(chat.getNickname());
        holder.TextView_msg.setText(chat.getMsg());

        if(chat.getNickname().equals(this.myNickname)){
            //자신의 메시지
            holder.TextView_msg.setBackgroundResource(R.drawable.rightchatbubble);
            holder.linearLayout_destination.setVisibility(View.INVISIBLE);
            holder.linearLayout_main.setGravity(Gravity.RIGHT);
        }
        else{ //다른 사용자의 메시지
            holder.TextView_msg.setBackgroundResource(R.drawable.leftchatbubble);
            holder.linearLayout_destination.setVisibility(View.VISIBLE);
            holder.linearLayout_main.setGravity(Gravity.LEFT);
        }

        //시간 설정
        long unixTime = (long) chat.timestamp;
        Date date = new Date(unixTime);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String time = simpleDateFormat.format(date);
        holder.TextView_time.setText(time);
    }

    @Override
    public int getItemCount(){
        return mDataset==null?0:mDataset.size();
    }

    public ChatData getChat(int position){
        return mDataset!=null?mDataset.get(position):null;
    }

    public void addChat(ChatData chat){
        mDataset.add(chat);
        notifyItemInserted(mDataset.size()-1);
    }
}
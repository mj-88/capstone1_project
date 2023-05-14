package com.team1.myapplication;

 public class ChatData {

     public String msg;
     public String nickname;

//     public String room;

     public Object timestamp;

     public String getMsg(){
        return msg;
    }
     public void setMsg(String msg){
         this.msg=msg;
     }

    public String getNickname(){
         return nickname;
     }

     public void setNickname(String nickname){
         this.nickname=nickname;
     }

//     public String getRoom(){
//         return room;
//     }
//     public void setRoom(String room){
//         this.room=room;
//     }
    public void setSendTime(Object timestamp){this.timestamp=timestamp;}

}
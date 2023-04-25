package com.team1.myapplication;

 public class ChatData {

     public String msg;
     public String nickname;

     public Object timestamp;
//     public String SendTime;

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

//    public Object getSendTime() {return timestamp;}
    public void setSendTime(Object timestamp){this.timestamp=timestamp;}

}
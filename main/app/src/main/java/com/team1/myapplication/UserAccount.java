package com.team1.myapplication;

//사용자 계정 정보 모델
public class UserAccount {
    private String emailId;
    private String password;
    private String idToken; //firebase 고유 토큰

    //클래스가 생성될 때 가장 먼저 호출되는 빈 생성자(firebase는 빈 생성자 필수)
    public UserAccount(){}

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.team1.myapplication;

public class MealUpload {
    private String saveDate;
    private String emailId;
    private String imageName;
    private String mealName;

    public MealUpload(String saveDate, String emailId, String imageName, String mealName){
    this.emailId = emailId;
    this.mealName = mealName;
    this.imageName = imageName;
    this.saveDate = saveDate;
    }



    public String getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(String saveDate) {
        this.saveDate = saveDate;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }
}

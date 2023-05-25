package com.team1.myapplication;

public class Food {

    private String emailId;
    private String imageName;
    private String mealName;
    private String saveDate;

    public Food(){}

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

    public String getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(String saveDate) {
        this.saveDate = saveDate;

    }

    public Food(String emailId, String imageName, String mealName, String saveDate) {
        this.emailId = emailId;
        this.imageName = imageName;
        this.mealName = mealName;
        this.saveDate = saveDate;
    }
}

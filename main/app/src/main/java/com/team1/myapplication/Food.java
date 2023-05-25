package com.team1.myapplication;

import android.net.Uri;

import com.google.firebase.storage.StorageReference;

public class Food {

    private String emailId;
    private String imageName;
    private String mealName;
    private String saveDate;

    private Uri imageUri;



    public Food(){}

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
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

    public String getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(String saveDate) {
        this.saveDate = saveDate;

    }

    public Food(String emailId, String imageName, String mealName, String saveDate, Uri imageUri) {
        this.emailId = emailId;
        this.imageName = imageName;
        this.mealName = mealName;
        this.saveDate = saveDate;
        this.imageUri = imageUri;
    }
}

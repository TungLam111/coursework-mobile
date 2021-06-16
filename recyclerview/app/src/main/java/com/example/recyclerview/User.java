package com.example.recyclerview;

public class User {
    String Title, Content, Link, PhoneNumber;
    int userPhoto;

    public User(String title, String content, int userPhoto, String link, String phoneNumber) {
        this.PhoneNumber = phoneNumber;
        this.Link = link;
        this.Title = title;
        this.Content = content;
        this.userPhoto = userPhoto;
    }

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }

    public int getUserPhoto() {
        return userPhoto;
    }

    public String getLink() {
        return Link;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }
}

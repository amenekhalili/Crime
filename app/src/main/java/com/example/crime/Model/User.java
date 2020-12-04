package com.example.crime.Model;

import java.util.UUID;

public class User {
    private UUID mUUID ;
    private String userName;
    private String passWord;

    public User(){
        this(UUID.randomUUID());
    }

    public User(UUID UUID) {
        mUUID = UUID;
    }

    public User(UUID UUID, String userName, String passWord) {
        mUUID = UUID;
        this.userName = userName;
        this.passWord = passWord;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }
}

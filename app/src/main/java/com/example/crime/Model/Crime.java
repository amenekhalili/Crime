package com.example.crime.Model;

import com.example.crime.Utils.DataUtils;

import java.util.Date;
import java.util.UUID;

public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private boolean mChecked;

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public Crime() {
       this(UUID.randomUUID());

    }

    public Crime(UUID id){
        mId = id;
        mDate = new Date();
    }

    public Crime(UUID id, String title, Date date, boolean solved, boolean checked) {
        mId = id;
        mTitle = title;
        mDate = date;
        mSolved = solved;
        mChecked = checked;
    }
}

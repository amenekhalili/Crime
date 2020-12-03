package com.example.crime.controller.Activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.crime.controller.Fragment.first_page_fragment;

public class first_page_activity extends SingleFragmentAvtivity {

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, first_page_activity.class);
        return intent;
    }

    @Override
    public Fragment CreateFragment() {
        return first_page_fragment.newInstance();
    }
}
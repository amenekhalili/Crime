package com.example.crime.controller.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.crime.R;
import com.example.crime.controller.Fragment.first_page_fragment;

public class first_page_activity extends AppCompatActivity {

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, first_page_activity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentById(R.id.container_fragment);

        first_page_fragment first_page_fragment1  = first_page_fragment.newInstance();
        if(fragment == null){

            fragmentManager
                    .beginTransaction()
                    .add(R.id.container_fragment ,first_page_fragment1)
                    .commit();
        }



    }
}
package com.example.crime.controller.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.crime.R;


public  abstract class SingleFragmentActivity extends AppCompatActivity {
   public abstract Fragment CreateFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentById(R.id.container_fragment);

        if(fragment == null){

            fragmentManager
                    .beginTransaction()
                    .add(R.id.container_fragment ,CreateFragment())
                    .commit();
        }
    }
}

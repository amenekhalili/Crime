package com.example.crime.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.crime.R;

public class CrimeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crimedetail);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentById(R.id.container_fragment);

                if(fragment == null){
                    CrimeDetailFragment crimeDetailFragment = new CrimeDetailFragment();
                    fragmentManager
                            .beginTransaction()
                            .add(R.id.container_fragment , crimeDetailFragment)
                            .commit();
                }



    }

}
package com.example.crime.controller.Activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.crime.controller.Fragment.CrimeDetailFragment;

import java.util.UUID;

public class CrimeDetailActivity extends SingleFragmentAvtivity {

    public static final String EXTRA_CRIME_ID = "CrimeId";


    public static Intent newIntent (Context context , UUID crimeId){
     Intent intent = new Intent(context , CrimeDetailActivity.class);
     intent.putExtra(EXTRA_CRIME_ID, crimeId);
     return intent;
 }


    @Override
    public Fragment CreateFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID );
        CrimeDetailFragment crimeDetailFragment = CrimeDetailFragment.newInstance(crimeId);
        return crimeDetailFragment;
    }
}
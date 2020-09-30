package com.example.crime.controller.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.crime.R;
import com.example.crime.controller.Fragment.CrimeListFragment;

public class CrimelistActivity extends SingleFragmentAvtivity {


    @Override
    public Fragment CreatFragment() {
        return new CrimeListFragment();
    }
}
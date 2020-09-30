package com.example.crime.controller.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.crime.R;
import com.example.crime.controller.Fragment.CrimeDetailFragment;

public class CrimeDetailActivity extends SingleFragmentAvtivity {


    @Override
    public Fragment CreatFragment() {
        return new CrimeDetailFragment();
    }
}
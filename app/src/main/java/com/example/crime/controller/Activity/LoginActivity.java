package com.example.crime.controller.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.crime.R;
import com.example.crime.controller.Fragment.LoginFragment;

import java.util.UUID;

public class LoginActivity extends SingleFragmentAvtivity {

    public static final String EXTRA_ID = "EXTRA_ID";

    public static Intent newIntent(Context context , UUID uuid){
        Intent intent = new Intent(context,LoginActivity.class);
        intent.putExtra(EXTRA_ID, uuid);
        return intent;
    }

    @Override
    public Fragment CreateFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_ID);
        return LoginFragment.newInstance(crimeId);
    }
}
package com.example.crime.controller.Activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.crime.controller.Fragment.SignUpFragment;

import java.util.UUID;

public class SignupActivity extends SingleFragmentActivity {

    public static final String EXTRA_ID = "EXTRA_ID";

    public static Intent newIntent(Context context , UUID uuid){
        Intent intent = new Intent(context,SignupActivity.class);
        intent.putExtra(EXTRA_ID, uuid);
        return intent;
    }

    @Override
    public Fragment CreateFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_ID);
        return SignUpFragment.newInstance(crimeId);
    }
}
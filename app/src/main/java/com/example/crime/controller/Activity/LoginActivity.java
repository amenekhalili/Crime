package com.example.crime.controller.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.crime.R;
import com.example.crime.controller.Fragment.LoginFragment;

public class LoginActivity extends SingleFragmentAvtivity {

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,LoginActivity.class);
        return intent;
    }

    @Override
    public Fragment CreateFragment() {
        return LoginFragment.newInstance();
    }
}
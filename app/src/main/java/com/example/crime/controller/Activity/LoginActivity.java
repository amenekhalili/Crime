package com.example.crime.controller.Activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.crime.controller.Fragment.Login_Fragment;
import com.example.crime.controller.Fragment.SignUpFragment;

import java.util.UUID;

public class LoginActivity extends SingleFragmentAvtivity {



    public static Intent newIntent(Context context ){
        Intent intent = new Intent(context,LoginActivity.class);
        return intent;
    }

    @Override
    public Fragment CreateFragment() {
        return Login_Fragment.newInstance();
    }
}
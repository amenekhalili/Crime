package com.example.crime.controller.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.crime.R;
import com.example.crime.Repository.CrimeRepository;
import com.example.crime.controller.Fragment.CrimeListFragment;
import com.example.crime.controller.Fragment.Empty_RecyclerView_Fragment;

public class CrimelistActivity extends SingleFragmentAvtivity {

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,CrimelistActivity.class);
        return intent;
    }


    @Override
    public Fragment CreateFragment() {

        if(CrimeRepository.getIsInstance().sizeList() == 0)
            return Empty_RecyclerView_Fragment.newInstance();
        else
            return CrimeListFragment.newInstance();
    }

}
package com.example.crime.controller.Activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.crime.Repository.CrimeDBRepository;
import com.example.crime.controller.Fragment.CrimeListFragment;
import com.example.crime.controller.Fragment.Empty_RecyclerView_Fragment;

public class CrimelistActivity extends SingleFragmentAvtivity {

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,CrimelistActivity.class);
        return intent;
    }


    @Override
    public Fragment CreateFragment() {

        if(CrimeDBRepository.getIsInstance(this).sizeList() == 0)
            return Empty_RecyclerView_Fragment.newInstance();
        else
            return CrimeListFragment.newInstance();
    }

}
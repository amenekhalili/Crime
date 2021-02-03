package com.example.crime.controller.Activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.crime.Model.Crime;
import com.example.crime.R;
import com.example.crime.Repository.CrimeDBRepository;
import com.example.crime.controller.Fragment.CrimeDetailFragment;
import com.example.crime.controller.Fragment.CrimeListFragment;
import com.example.crime.controller.Fragment.Empty_RecyclerView_Fragment;

public class CrimelistActivity extends SingleFragmentAvtivity
         implements CrimeListFragment.Callbacks , CrimeDetailFragment.Callbacks {

    public static final String EXTRA_NAME = "EXTRA_NAME";

    public static Intent newIntent(Context context , String UserName){
        Intent intent = new Intent(context,CrimelistActivity.class);
        intent.putExtra(EXTRA_NAME, UserName);
        return intent;
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,CrimelistActivity.class);
        return intent;
    }

    @Override
    public Fragment CreateFragment() {


    String UserName = getIntent().getStringExtra(EXTRA_NAME);

        if(CrimeDBRepository.getIsInstance(this).sizeList() == 0)
            return Empty_RecyclerView_Fragment.newInstance(UserName);
        else
            return CrimeListFragment.newInstance(UserName);
    }

    @Override
    public void onCrimeSelected(Crime crime) {

        if(findViewById(R.id.container_fragment_detail) == null){
            Intent intent = CrimePagerActivity.newIntent(this , crime.getId());
            startActivity(intent);
        }
        else{
            CrimeDetailFragment crimeDetailFragment = CrimeDetailFragment.newInstance(crime.getId());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_fragment_detail , crimeDetailFragment).commit();

        }


    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment crimeListFragment = (CrimeListFragment)
                getSupportFragmentManager().
                        findFragmentById(R.id.container_fragment);

        crimeListFragment.updateUI();

    }
}
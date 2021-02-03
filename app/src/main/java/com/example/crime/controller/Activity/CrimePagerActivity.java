package com.example.crime.controller.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.crime.Model.Crime;
import com.example.crime.R;
import com.example.crime.Repository.CrimeDBRepository;
import com.example.crime.Repository.IRepository;
import com.example.crime.controller.Fragment.CrimeDetailFragment;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity implements CrimeDetailFragment.Callbacks {
    public static final String EXTRA_CRIME_ID = "com.example.crime.controller.CRIME_ID";
    private ViewPager2 mViewPager2;
    private IRepository mCrimeRepository;
    private UUID crimeId;


    private int currentIndex;


    public static Intent newIntent(Context context, UUID crimeId) {
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
        mCrimeRepository = CrimeDBRepository.getIsInstance(this);
        crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);


       findViews();
        initViews();

    }


    private void findViews() {
        mViewPager2 = findViewById(R.id.View_pager_Crime);


    }

    private void initViews() {
        final List<Crime> crimes = mCrimeRepository.getCrimes();
        final crimePagerAdapter crimePagerAdapter = new crimePagerAdapter(this , crimes);
        mViewPager2.setAdapter(crimePagerAdapter);
        currentIndex = mCrimeRepository.getPosition(crimeId);
        mViewPager2.setCurrentItem(currentIndex );
        mViewPager2.setPageTransformer(new zoomAnimation());
        }

    @Override
    public void onCrimeUpdated(Crime crime) {
        
    }

    public class crimePagerAdapter extends FragmentStateAdapter {
        private List<Crime> mCrimes;


        public List<Crime> getCrimes() {
            return mCrimes;
        }

        public void setCrimes(List<Crime> crimes) {
            mCrimes = crimes;
        }

        public crimePagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Crime> crimes) {
            super(fragmentActivity);
            mCrimes = crimes;
        }



        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Crime crime = mCrimes.get(position);
            CrimeDetailFragment crimeDetailFragment = CrimeDetailFragment.newInstance(crime.getId());
            return crimeDetailFragment;
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }



    }


   }

package com.example.crime.controller.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crime.Model.Crime;
import com.example.crime.R;
import com.example.crime.Repository.CrimeRepository;
import com.example.crime.Repository.IRepository;
import com.example.crime.controller.Activity.CrimePagerActivity;

import java.util.List;


public class CrimeListFragment extends Fragment {

    public static final String SUBTITLE_VISIBILITY = "subtitleVisibility";
    private RecyclerView mRecyclerView;
    private IRepository mCrimeRepository;
    private CrimeAdapter crimeAdapter;
    private boolean isSubtitleVisible = false;


    public static CrimeListFragment newInstance() {

        Bundle args = new Bundle();
        CrimeListFragment fragment = new CrimeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CrimeListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCrimeRepository = CrimeRepository.getIsInstance();
        setHasOptionsMenu(true);
           if(savedInstanceState != null)
               isSubtitleVisible = savedInstanceState.getBoolean(SUBTITLE_VISIBILITY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        findViews(view);
        initViews();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.list_fragment_menu , menu);
        MenuItem item = menu.findItem(R.id.menu_item_subtitle_crime);
        setTxtSubTitle(item);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.meu_item_add_crime:
                Crime crime = new Crime();
                CrimeRepository.getIsInstance().insertCrime(crime);

                Intent intent = CrimePagerActivity.newIntent(getActivity() , crime.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_subtitle_crime:
                     isSubtitleVisible = ! isSubtitleVisible;
                updateSubtitle();
                setTxtSubTitle(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setTxtSubTitle(@NonNull MenuItem item) {
        item.setTitle(isSubtitleVisible ? R.string.subtitle_item_hide :
                R.string.subtitle_item_show);
    }

    private void updateSubtitle() {
        int size = CrimeRepository.getIsInstance().sizeList();
        String numberOfCrimes = isSubtitleVisible ? size + " Crime" : null;
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(numberOfCrimes);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(SUBTITLE_VISIBILITY, isSubtitleVisible);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        updateSubtitle();
    }

    private void updateUI() {
        List<Crime> crimes = mCrimeRepository.getCrimes();
        if (crimeAdapter == null) {
            crimeAdapter = new CrimeAdapter(crimes);
            mRecyclerView.setAdapter(crimeAdapter);
        } else {
            crimeAdapter.notifyDataSetChanged();
        }

    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

    }

    private class CrimeHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewTitle;
        private TextView mTextViewDate;
        private ImageView mImageViewSolved;
        private Crime mCrime;

        public CrimeHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewTitle = itemView.findViewById(R.id.row_title_crime);
            mTextViewDate = itemView.findViewById(R.id.row_date_crime);
            mImageViewSolved = itemView.findViewById(R.id.image_solved);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
                    startActivity(intent);
                }
            });
        }

        public void bindCrime(Crime crime) {
            mTextViewTitle.setText(crime.getTitle());
            mTextViewDate.setText(crime.getDate().toString());
            mCrime = crime;
            mImageViewSolved.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }


    }


    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public List<Crime> getCrimes() {
            return mCrimes;
        }

        public void setCrimes(List<Crime> crimes) {
            mCrimes = crimes;
        }

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.crime_row_list, parent, false);

            CrimeHolder crimeHolder = new CrimeHolder(view);
            return crimeHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.list_recyclerview);
    }
}
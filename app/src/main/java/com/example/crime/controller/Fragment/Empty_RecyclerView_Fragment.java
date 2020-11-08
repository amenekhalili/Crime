package com.example.crime.controller.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.crime.Model.Crime;
import com.example.crime.R;
import com.example.crime.Repository.CrimeRepository;
import com.example.crime.controller.Activity.CrimePagerActivity;
import com.example.crime.controller.Activity.CrimelistActivity;

public class Empty_RecyclerView_Fragment extends Fragment {
    private ImageButton mImageButton;


    public Empty_RecyclerView_Fragment() {
        // Required empty public constructor
    }
    public static Empty_RecyclerView_Fragment newInstance() {
        Empty_RecyclerView_Fragment fragment = new Empty_RecyclerView_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_empty_recyclerview, container,
                false);

        findViews(view);
        setListener();

        return view;
    }

    private void setListener() {
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crime crime = new Crime();
                CrimeRepository.getIsInstance().insertCrime(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity() , crime.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(CrimeRepository.getIsInstance().sizeList() !=  0){
            Intent intent = CrimelistActivity.newIntent(getActivity());
            startActivity(intent);
        }
    }

    private void findViews(View view) {
        mImageButton = view.findViewById(R.id.imgbtn_add_crime);
    }
}
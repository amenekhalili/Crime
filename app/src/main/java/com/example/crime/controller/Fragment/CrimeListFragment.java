package com.example.crime.controller.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crime.R;


public class CrimeListFragment extends Fragment {
  private  RecyclerView mRecyclerView;


    public CrimeListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_crime_list, container, false);

        findViews(view);
        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.list_recyclerview);
    }
}
package com.example.crime.controller.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.crime.Model.Crime;
import com.example.crime.R;

import java.util.Date;


public class CrimeDetailFragment extends Fragment {
  private EditText mEditTexttitle;
  private Button mButtondate;
  private CheckBox mCheckBoxsolved;
    private  Crime mCrime;

    public CrimeDetailFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCrime = new Crime();
        mCrime.setTitle("TESTING THE TITLE");
        mCrime.setSolved(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_crime_detail , container , false);
         findViews(view);

        initViews();
        setlistener();
      return  view;
    }

    private void initViews() {
        mEditTexttitle.setText(mCrime.getTitle());
        mCheckBoxsolved.setChecked(mCrime.isSolved());
        mButtondate.setText(mCrime.getDate().toString());
        mButtondate.setEnabled(false);
    }

    private void setlistener() {

        mCheckBoxsolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        mButtondate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void findViews(View view) {
        mEditTexttitle = view.findViewById(R.id.Edittext_title);
        mCheckBoxsolved = view.findViewById(R.id.checkbox_solved);
        mButtondate = view.findViewById(R.id.btn_date);

    }
}
package com.example.crime.controller.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.crime.Model.Crime;
import com.example.crime.R;
import com.example.crime.Repository.CrimeRepository;
import com.example.crime.Repository.IRepository;
import com.example.crime.controller.Activity.CrimeDetailActivity;

import java.util.UUID;


public class CrimeDetailFragment extends Fragment {
  private EditText mEditTexttitle;
  private Button mButtondate;
  private CheckBox mCheckBoxsolved;
    private  Crime mCrime;
    public static final String ARG_CRIME_ID = "ArgcrimeId";
    private IRepository mCrimeRepository;

    public static CrimeDetailFragment newInstance(UUID crimeId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID,crimeId);
        CrimeDetailFragment fragment = new CrimeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CrimeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCrime();
    }

    private void updateCrime() {
        mCrimeRepository.updateCrime(mCrime);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrimeRepository = CrimeRepository.getIsInstance();

       UUID id = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = mCrimeRepository.getCrime(id);
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

        mEditTexttitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCheckBoxsolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                     mCrime.setSolved(isChecked);
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
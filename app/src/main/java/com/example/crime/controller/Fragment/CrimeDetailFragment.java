package com.example.crime.controller.Fragment;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crime.Model.Crime;
import com.example.crime.R;
import com.example.crime.Repository.CrimeRepository;
import com.example.crime.Repository.IRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class CrimeDetailFragment extends Fragment {
    public static final String DATA_PICKER_TAG = "Data_picker_Tag";
    public static final int REQUEST_CODE_DATA_PICKER = 0;
    public static final String TIME_PICKER_TAG = "Time_picker_Tag";
    public static final int REQUEST_CODE_TIME_PICKER = 1;
    private EditText mEditTexttitle;
    private Button mButtondate;
    private Button mButtonTime;
    private CheckBox mCheckBoxsolved;
    private Crime mCrime;
    public static final String ARG_CRIME_ID = "ArgcrimeId";
    private IRepository mCrimeRepository;
    private ImageButton btn_doubleLeft;
    private ImageButton btn_doubleRight;
    private ImageButton btn_pre;
    private ImageButton btn_next;
    private UUID id;
    private int currentIndex;
    private Date mCrimeDate ;
    private int year;
    private int monthOfYear;
    private int dayOfMonth;
    private int hour;
    private int minute;
    private int second;

    private ViewPager2 viewPager2;

    public static CrimeDetailFragment newInstance(UUID crimeId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);
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
        id = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = mCrimeRepository.getCrime(id);
        currentIndex = mCrimeRepository.getPosition(id);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_detail, container, false);
        findViews(view);

        initViews();
        setlistener();
        return view;
    }

    private void initViews() {
        extractDate();

        mEditTexttitle.setText(mCrime.getTitle());
        mCheckBoxsolved.setChecked(mCrime.isSolved());
        mButtondate.setText( String.format("%02d/%02d/%02d" , year , monthOfYear+1 , dayOfMonth));
        mButtonTime.setText(String.format("%02d:%02d:%02d" , hour , minute , second));

    }

    private void extractDate() {
        Calendar calendar = Calendar.getInstance();
        mCrimeDate = mCrime.getDate();
        calendar.setTime(mCrimeDate);
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
    }

    private void setlistener() {

        final int crimeListSize = mCrimeRepository.sizeList();
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
                DataPickerFragment dataPickerFragment = DataPickerFragment.newInstance(mCrime.getDate());
                dataPickerFragment.setTargetFragment(CrimeDetailFragment.this
                        , REQUEST_CODE_DATA_PICKER);

                dataPickerFragment.show(getActivity().

                        getSupportFragmentManager(), DATA_PICKER_TAG);

            }
        });

        mButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mCrime.getDate());
                timePickerFragment.setTargetFragment(CrimeDetailFragment.this
                        , REQUEST_CODE_TIME_PICKER);

                timePickerFragment.show(getActivity().
                        getSupportFragmentManager(), TIME_PICKER_TAG);
            }
        });


        btn_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex - 1 + crimeListSize) % crimeListSize;
                viewPager2.setCurrentItem(currentIndex);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1) % (crimeListSize);
                viewPager2.setCurrentItem(currentIndex);
            }
        });

        btn_doubleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(99);
            }
        });

        btn_doubleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(0);
            }
        });
    }


    private void findViews(View view) {
        mEditTexttitle = view.findViewById(R.id.Edittext_title);
        mCheckBoxsolved = view.findViewById(R.id.checkbox_solved);
        mButtondate = view.findViewById(R.id.btn_date);
        btn_doubleLeft = view.findViewById(R.id.btn_double_left);
        btn_doubleRight = view.findViewById(R.id.btn_double_right);
        btn_next = view.findViewById(R.id.btn_next);
        btn_pre = view.findViewById(R.id.btn_pre);
        viewPager2 = getActivity().findViewById(R.id.View_pager_Crime);
        mButtonTime = view.findViewById(R.id.btn_time);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        ;

        if (requestCode == REQUEST_CODE_DATA_PICKER) {
            Date userSelectedDate = (Date) data.getSerializableExtra(DataPickerFragment.EXTRA_USER_SELECTED_DATA);
            updateCrimeDate(userSelectedDate);
        }

        if(requestCode == REQUEST_CODE_TIME_PICKER){

          int  hour = data.getIntExtra(TimePickerFragment.EXTRA_HOUR_DATE, 0);
          int minute = data.getIntExtra(TimePickerFragment.EXTRA_MINUTE_DATE , 0);
          updateCrimeTime(hour , minute);
        }

    }
    private void updateCrimeTime(int hour , int minute) {
        second = 0;
        mButtonTime.setText(String.format("%02d:%02d:%02d" , hour , minute , second));
    }


    private void updateCrimeDate(Date userSelectedDate) {
        mCrime.setDate(userSelectedDate);
        updateCrime();
        extractDate();
        mButtondate.setText(String.format("%02d/%02d/%02d" , year ,monthOfYear+1 , dayOfMonth));

    }
}
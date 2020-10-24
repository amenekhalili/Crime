package com.example.crime.controller.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.example.crime.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class TimePickerFragment extends DialogFragment {
    public static final String EXTRA_CRIME_DATE = "EXTRA_CRIME_DATE";

    public static final String EXTRA_HOUR_DATE = "Extra_hour_date";
    public static final String EXTRA_MINUTE_DATE = "Extra_minute_date";
    private Date mCrimeDate ;
    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(Date mCrimeDate) {
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
         args.putSerializable(EXTRA_CRIME_DATE, mCrimeDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCrimeDate = (Date) getArguments().getSerializable(EXTRA_CRIME_DATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_time_picker, container, false);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view  = layoutInflater.inflate(R.layout.fragment_time_picker , null);

        findViews(view);
        initViews();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("TimeOfCrime")
                .setView(view)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hour = mTimePicker.getHour();
                        int minute = mTimePicker.getMinute();

                        sendResult(hour , minute);
                    }
                });

        AlertDialog alertDialog = builder.create();
        return alertDialog;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initViews() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCrimeDate);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        mTimePicker.is24HourView();
        mTimePicker.setHour(hour);
        mTimePicker.setMinute(minute);
    }

    private void sendResult(int hour , int minute){
        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_HOUR_DATE, hour);
        intent.putExtra(EXTRA_MINUTE_DATE, minute);
        fragment.onActivityResult(requestCode , resultCode , intent);
    }

    private void findViews(View view) {
        mTimePicker = view.findViewById(R.id.time_picker_date);
    }
}
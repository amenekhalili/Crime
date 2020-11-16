package com.example.crime.controller.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.crime.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DataPickerFragment extends DialogFragment {
    public static final String ARG_CRIME_DATE = "ARG_CRIME_DATE";
    public static final String EXTRA_USER_SELECTED_DATA = "EXTRA_USER_SELECTED_DATA";
    public static final String EXTRA_YEAR = "Extra_year";
    public static final String EXTRA_MONTH_OF_YEAR = "Extra_monthOfYear";
    public static final String EXTRA_DAY_OF_MONTH = "Extra_dayOfMonth";
    private Date mCrimeDate ;
    private DatePicker mDatePicker;

    public static DataPickerFragment newInstance(Date mCrimeDate) {
        DataPickerFragment fragment = new DataPickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_DATE, mCrimeDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCrimeDate = (Date) getArguments().getSerializable(ARG_CRIME_DATE);
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view  = layoutInflater.inflate(R.layout.fragment_data_picker , null);
        findViews(view);
        initViews();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
               .setTitle(R.string.DataPicker_Title)
               .setMessage("Crime!!!")
               .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       int year = mDatePicker.getYear();
                       int monthOfYear = mDatePicker.getMonth();
                       int dayOfMonth = mDatePicker.getDayOfMonth();
                           sendResult(year , monthOfYear , dayOfMonth);
                   }
               })
               .setNegativeButton(android.R.string.cancel , null)
               .setView(view);
        AlertDialog alertDialog = builder.create();
        return alertDialog;
    }

    private void initViews() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCrimeDate);
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        mDatePicker.init(year , monthOfYear , dayOfMonth , null);

    }

    private void findViews(View view) {
        mDatePicker = view.findViewById(R.id.data_picker_crime);
    }

    private void sendResult(int year , int monthOfYear , int dayOfMonth){
        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_YEAR, year);
        intent.putExtra(EXTRA_MONTH_OF_YEAR, monthOfYear);
        intent.putExtra(EXTRA_DAY_OF_MONTH, dayOfMonth);
        fragment.onActivityResult(requestCode , resultCode , intent);
    }
}
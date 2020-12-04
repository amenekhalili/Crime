package com.example.crime.controller.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.crime.Model.User;
import com.example.crime.R;

import java.util.UUID;

import com.example.crime.Repository.IRepositoryUser;
import com.example.crime.Repository.UserDBRepository;

public class LoginFragment extends Fragment {
    public static final String ARG_ID = "ARG_ID";
    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private User mUser;
    private UUID id;
    private IRepositoryUser mIRepositoryUser;

    public static LoginFragment newInstance(UUID uuid) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ID,uuid );
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIRepositoryUser = UserDBRepository.getIsInstance(getActivity());
      id = (UUID) getArguments().getSerializable(ARG_ID);
      mUser = mIRepositoryUser.getUser(id);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_login, container, false);
        findViews(view);
        setListener();


        return view;
    }

    private void setListener() {
        mEditTextUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               mUser.setUserName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               mUser.setPassWord(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void findViews(View view) {
        mEditTextUserName = view.findViewById(R.id.editText_login_UserName);
        mEditTextPassword = view.findViewById(R.id.editText_login_PassWord);
    }
}
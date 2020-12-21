package com.example.crime.controller.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.crime.Model.User;
import com.example.crime.R;
import com.example.crime.Repository.UserDBRepository;
import com.example.crime.Repository.UserIRepository;
import com.example.crime.controller.Activity.CrimePagerActivity;
import com.example.crime.controller.Activity.CrimelistActivity;
import com.example.crime.controller.Activity.SignupActivity;

import java.util.List;

public class first_page_fragment extends Fragment {
    private Button mButtonLogin;
    private Button mButtonSignUp;
    private UserIRepository mUserDBRepository;
    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private String UserName;
    private String Password;
    private boolean flagValidUser = false;

    public static first_page_fragment newInstance() {
        first_page_fragment fragment = new first_page_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserDBRepository = UserDBRepository.getIsInstance(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_page, container, false);

        findViews(view);
        setListener();

        return view;
    }
    private void findViews(View view) {
        mButtonLogin = view.findViewById(R.id.btn_login);
        mButtonSignUp = view.findViewById(R.id.btn_signup);
        mEditTextUserName = view.findViewById(R.id.userName_login);
        mEditTextPassword = view.findViewById(R.id.password_login);
    }
    private void setListener() {

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserName = mEditTextUserName.getText().toString();
                Password = mEditTextPassword.getText().toString();
                User user = mUserDBRepository.validUser(Password);
                if(user != null  && UserName.equals(user.getUserName())){
                    Intent intent = CrimelistActivity.newIntent(getActivity() , user.getUserName());
                    startActivity(intent);
                }else if (user == null || !UserName.equals(user.getUserName())){
                    Toast.makeText(getActivity(), "INVALID USER!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                mUserDBRepository.insertUser(user);
                Intent intent = SignupActivity.newIntent(getActivity(), user.getUUID());
                startActivity(intent);
            }
        });
    }


}
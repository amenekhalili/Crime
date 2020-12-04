package com.example.crime.controller.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.crime.Model.User;
import com.example.crime.R;
import com.example.crime.Repository.UserDBRepository;
import com.example.crime.controller.Activity.LoginActivity;
import com.example.crime.controller.Activity.SignupActivity;
import com.example.crime.Repository.IRepositoryUser;

public class first_page_fragment extends Fragment {
    private Button mButtonLogin;
    private Button mButtonSignUp;
    private IRepositoryUser mUserDBRepository;


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
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_first_page, container, false);

        findViews(view);
        setListener();

        return view;
    }

    private void setListener() {

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                mUserDBRepository.insertUser(user);
                Intent intent = LoginActivity.newIntent(getActivity() , user.getUUID());
                startActivity(intent);
            }
        });

        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "SignUp Clicked" , Toast.LENGTH_SHORT).show();
                Intent intent = SignupActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });
    }

    private void findViews(View view) {
        mButtonLogin = view.findViewById(R.id.btn_login);
        mButtonSignUp = view.findViewById(R.id.btn_signup);
    }
}
package com.frankcode1.onlinerehab.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.frankcode1.onlinerehab.Login;
import com.frankcode1.onlinerehab.R;
import com.frankcode1.onlinerehab.SignUp;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        final TextView app_name = root.findViewById(R.id.txt_app_name);
        final TextView about = root.findViewById(R.id.txt_about);
        final TextView social = root.findViewById(R.id.text_social);
        final Button login = root.findViewById(R.id.btn_login);
        final Button signup = root.findViewById(R.id.btn_signup);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "tuende kwa about", Toast.LENGTH_LONG).show();
            }
        });

        social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"tuende kwa social media", Toast.LENGTH_LONG).show();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goLogin = new Intent(getActivity(), Login.class);
                startActivity(goLogin);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "signup",Toast.LENGTH_LONG).show();
                Intent goSignUp = new Intent(getActivity(), SignUp.class);
                startActivity(goSignUp);
            }
        });

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
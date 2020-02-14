package com.frankcode1.onlinerehab;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        Button login0 = findViewById(R.id.butt_login);
        TextView newUser = findViewById(R.id.txt_signupt);

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup1 = new Intent(Login.this, SignUp.class);
                startActivity(signup1);
            }
        });
        login0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }

            private void login() {
                EditText email = findViewById(R.id.edit_mail);
                EditText pssw = findViewById(R.id.edit_password);
                final ProgressBar loader = findViewById(R.id.re_progress);

                String mail = email.getText().toString().trim();
                String password = pssw.getText().toString().trim();

                if (mail.isEmpty()) {
                    email.setError("Email is Required");
                    email.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    pssw.setError("Password is Required");
                    pssw.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                    email.setError("Enter a Valid Email");
                    email.requestFocus();
                    return;
                }
                loader.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        loader.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Use Logged in Successfully", Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(Login.this, UserProfile.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
                        }
                        else{
//                            Toast.makeText(Login.this,"User Login failed",Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

}

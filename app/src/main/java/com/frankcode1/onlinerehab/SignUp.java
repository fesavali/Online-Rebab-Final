package com.frankcode1.onlinerehab;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        Button signup0 = findViewById(R.id.btn_sgn);
        TextView toLogin = findViewById(R.id.txt_to_login);

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLogin = new Intent(SignUp.this, Login.class);
                startActivity(toLogin);
            }
        });
        signup0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }
    private void register() {
        EditText mail = findViewById(R.id.edt_email);
        EditText pass = findViewById(R.id.edt_password);
        EditText pass2 = findViewById(R.id.edt_confirm);
        final ProgressBar loader = findViewById(R.id.re_progress);

        String email = mail.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String password2 = pass2.getText().toString().trim();

        if (email.isEmpty()) {
            mail.setError("Email is Required");
            mail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            pass.setError("Password is Required");
            pass.requestFocus();
            return;
        }
        if (password2.isEmpty()) {
            pass2.setError("You need to Confirm Password");
            pass2.requestFocus();
            return;
        }
        if (password.length() < 6) {
            pass.setError("Minimum 6 characters");
            pass.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mail.setError("Enter a Valid Email");
            mail.requestFocus();
            return;
        }
        if(!password.matches(password2)){
            pass2.setError("Passwords do not Match");
            pass2.requestFocus();
            return;
        }

        loader.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        loader.setVisibility(View.GONE);
                        if(task.getException() instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(SignUp.this, "Email already Registered",Toast.LENGTH_LONG).show();
                        }
                        if(task.isSuccessful()){
                            Log.d(getCallingPackage(), "createUserWithEmail:success");
                            Toast.makeText(SignUp.this, "User Registered Successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignUp.this, Register.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }else {
//                            Toast.makeText(SignUp.this, "Registration Not Successfull", Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

}

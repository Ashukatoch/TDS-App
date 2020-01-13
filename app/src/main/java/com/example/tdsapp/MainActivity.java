package com.example.tdsapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
{
    private EditText email,password;
    private Button signin,signup;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener listener;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.emailedittext);
        password=findViewById(R.id.passwordedittext);
        signup=findViewById(R.id.login_signupbutton);
        signin=findViewById(R.id.login_signinbutton);
        mauth=FirebaseAuth.getInstance();
        listener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = mauth.getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }
            }
        };
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memail=email.getText().toString().trim();
                String mpass=password.getText().toString().trim();
                if(TextUtils.isEmpty(memail)) {
                    email.setError("Required Field...");
                    return;
                }
                if(TextUtils.isEmpty(mpass)) {
                    password.setError("Required Field...");
                    return;
                }
   mauth.signInWithEmailAndPassword(memail,mpass).addOnCompleteListener(MainActivity.this,new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful())
        {
            email.setText("");
            password.setText("");
            Log.d("SIgn in","Success");
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
            Toast.makeText(getApplicationContext(),"Sign in successfull",Toast.LENGTH_LONG).show();
        }
        else
        {
            Log.d("Sign in","failed");
            Toast.makeText(getApplicationContext(),"Sign in failed",Toast.LENGTH_LONG).show();
        }
    }
});

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
            }
        });

    }
}

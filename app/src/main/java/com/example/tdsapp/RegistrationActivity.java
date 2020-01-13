package com.example.tdsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {
    private EditText email,password;
    private Button reg_signin,reg_signup;
    private FirebaseAuth mauth1;
    private FirebaseAuth.AuthStateListener listener;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        email=findViewById(R.id.reg_emailedittext);
        password=findViewById(R.id.reg_passwordedittext);
        reg_signup=findViewById(R.id.reg_signupbutton);
        reg_signin=findViewById(R.id.reg_signinbutton);
        mauth1=FirebaseAuth.getInstance();
        listener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user=mauth1.getCurrentUser();
                if(user!=null)
                {
                    mauth1.signOut();
                    //mauth.signOut();
                    //startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }

            }
        };
        reg_signup.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                          mgr.hideSoftInputFromWindow(email.getWindowToken(), 0);
                                          mgr.hideSoftInputFromWindow(password.getWindowToken(), 0);
                                          final String memail = email.getText().toString().trim();
                                          final String mpass = password.getText().toString().trim();
                                          if (TextUtils.isEmpty(memail)) {
                                              email.setError("Required Field...");
                                              return;
                                          }
                                          if (TextUtils.isEmpty(mpass)) {
                                              password.setError("Required Field...");
                                              return;
                                          }

                                          mauth1.createUserWithEmailAndPassword(memail, mpass).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                              @Override
                                              public void onComplete(@NonNull Task<AuthResult> task) {
                                                  if (task.isSuccessful())
                                                  {
                                                      email.setText("");
                                                      password.setText("");
                                                      Toast.makeText(getApplicationContext(), "Sign up successfull", Toast.LENGTH_LONG).show();
                                                     // startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                                      mauth1.signInWithEmailAndPassword(memail,mpass).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                                          @Override
                                                          public void onComplete(@NonNull Task<AuthResult> task) {
                                                              if (task.isSuccessful()) {
                                                                  startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                                                  Toast.makeText(getApplicationContext(), "Sign in successfull", Toast.LENGTH_LONG).show();
                                                              } else
                                                                  {
                                                                      Toast.makeText(getApplicationContext(), " Error", Toast.LENGTH_LONG).show();
                                                              }
                                                          }

                                                      });
                                                      //finish();
                                                  } else
                                                      {

                                                      Toast.makeText(getApplicationContext(), "Error signing up", Toast.LENGTH_LONG).show();
                                                  }
                                              }
                                          });
                                      }
                                  });

                reg_signin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        //finish();
                    }
                });
            }
        }

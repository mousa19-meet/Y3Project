package com.example.blah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText email, password;
    Button signin, signup;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fetching views from xml
        progressBar = findViewById(R.id.progressBar);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);

        //Fetching Firebase
        mAuth = FirebaseAuth.getInstance();

        //Sign In through firebase Authentication
        signin.setOnClickListener(view -> {
            String Temail = email.getText().toString();
            String Tpassword = password.getText().toString();
            if ( !Temail.isEmpty() && !Tpassword.isEmpty()){
                {
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(Temail, Tpassword).addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(MainActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        // Different way of implementing button onClickListener than above button.
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
         if (signup == view){
            signUpPage();
        }
    }

    public void signUpPage(){
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

}
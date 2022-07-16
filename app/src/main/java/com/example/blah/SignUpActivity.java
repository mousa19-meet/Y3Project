package com.example.blah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText email, password, name;
    Button signup;
    Button cancel;
    FirebaseAuth mAuth;
    FirebaseDatabase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        signup = findViewById(R.id.signup);
        cancel = findViewById(R.id.cancel);

        mAuth = FirebaseAuth.getInstance();
        DB = FirebaseDatabase.getInstance("https://blahblah-97064-default-rtdb.firebaseio.com/");

        cancel.setOnClickListener(this);
        signup.setOnClickListener(this);
        /*
        signup.setOnClickListener(view -> {
            String Tname = name.getText().toString();
            String Temail = email.getText().toString();
            String Tpassword = password.getText().toString();
            if ( !Temail.isEmpty() && !Tpassword.isEmpty() && !Tname.isEmpty()){
                mAuth.createUserWithEmailAndPassword(Temail, Tpassword)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    User user = new User(Tname, Temail, Tpassword);
                                    String uid = mAuth.getCurrentUser().getUid();
                                    DB.getReference("Users").child(uid).setValue(user);
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
        */
    }

    @Override
    public void onClick(View view) {
         if (cancel == view){
            cancelButton();
        }
         else if (signup == view){
             createAccount(email.getText().toString(), password.getText().toString(), name.getText().toString());
         }
    }

    public void cancelButton(){
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void createAccount(String email, String password, String name) {
        if (email != null && password != null && name != null) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                User user = new User(name, email, password);
                                String uid = mAuth.getCurrentUser().getUid().toString();
                                DB.getReference("Users").child(uid).setValue(user);
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}
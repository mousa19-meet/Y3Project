package com.example.blah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        //Fetching views from xml
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        signup = findViewById(R.id.signup);
        cancel = findViewById(R.id.cancel);

        //Fetching Firebase
        mAuth = FirebaseAuth.getInstance();
        DB = FirebaseDatabase.getInstance("https://blahblah-97064-default-rtdb.firebaseio.com/");

        // Two ways of implementing OnClickListener
        cancel.setOnClickListener(this);
        signup.setOnClickListener(view -> {
            String Tname = name.getText().toString();
            String Temail = email.getText().toString();
            String Tpassword = password.getText().toString();
            {
                if (Temail != null && Tpassword != null && Tname != null) {
                    mAuth.createUserWithEmailAndPassword(Temail, Tpassword)
                            .addOnCompleteListener(this, task -> {
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
                            });
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
         if (cancel == view){
            cancelButton();
        }
    }

    public void cancelButton(){
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
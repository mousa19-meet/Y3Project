package com.example.blah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UploadActivity extends AppCompatActivity {

    EditText StatusQuoName, StatusQuoDescription;
    Button upload, cancel;
    CheckBox switchSolution;
    FirebaseDatabase DB;
    DatabaseReference DBR, DBRS;
    FirebaseAuth mAuth;
    FirebaseUser Fireuser;
    String Username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        //Fetching views from xml
        StatusQuoDescription = findViewById(R.id.statusQuoText);
        StatusQuoName = findViewById(R.id.statusQuoName);
        upload = findViewById(R.id.Upload);
        cancel = findViewById(R.id.cancel);
        switchSolution = findViewById(R.id.statusQuoSwitch);

        //Fetching Firebase
        DB = FirebaseDatabase.getInstance("https://blahblah-97064-default-rtdb.firebaseio.com/");
        DBR = DB.getReference("Users");
        DBRS = DB.getReference("Startups");
        mAuth = FirebaseAuth.getInstance();

        //Fetching Current User information
        Fireuser = mAuth.getCurrentUser();
        String uid = Fireuser.getUid();

        //Fetching Current User username
        DBR.child(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                HashMap<String, String> value = (HashMap<String, String>) task.getResult().getValue();
                Username = value.get("name");
            }
        });


        upload.setOnClickListener(view -> {
           createStartUp(StatusQuoName.getText().toString(),
                   StatusQuoDescription.getText().toString(),
                   Username,
                   switchSolution.isChecked());
        });

        cancel.setOnClickListener(view -> {
            Intent intent = new Intent(UploadActivity.this, ContentActivity.class);
            startActivity(intent);
        });
    }

    public void createStartUp(String name, String statusQuo, String owner, boolean hasSolution) {
        if(!name.isEmpty() && !statusQuo.isEmpty()){
            StartUp startup = new StartUp(name, statusQuo, owner, hasSolution);

            DatabaseReference postsRef = DBRS;
            DatabaseReference newPostRef = postsRef.push();
            String postId = newPostRef.getKey();

            DBRS.child(postId).setValue(startup);

            Intent intent = new Intent(UploadActivity.this, ContentActivity.class);
            startActivity(intent);
        } else {
            // If fields are empty, display a message to the user.
            Toast.makeText(UploadActivity.this, "Please Fill Out Fields",
                    Toast.LENGTH_LONG).show();
        }
    }

}
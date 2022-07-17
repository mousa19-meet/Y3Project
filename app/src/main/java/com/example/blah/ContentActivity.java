package com.example.blah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ContentActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser Fireuser;
    FirebaseDatabase DB;
    DatabaseReference DBR, DBRStartups;
    TextView userEmail;
    Button signout, upload;
    ListView startupsListView;
    ArrayList<StartUp> startups;
    ArrayAdapter<StartUp> arrayAdapter;
    String Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        //Fetching views from xml
        userEmail = findViewById(R.id.userEmail);
        signout = findViewById(R.id.signout);
        upload = findViewById(R.id.upload);

        //Fetching Firebase
        mAuth = FirebaseAuth.getInstance();
        Fireuser = mAuth.getCurrentUser();
        DB = FirebaseDatabase.getInstance("https://blahblah-97064-default-rtdb.firebaseio.com");
        DBR = DB.getReference("Users");
        DBRStartups = DB.getReference("Startups");

        //Fetching Current user in session's Unique id
        String uid = Fireuser.getUid();

        //Fetching Current user's Username & it showing on screen
        DBR.child(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                HashMap<String, String> value = (HashMap<String, String>) task.getResult().getValue();
                if (value != null ){
                    Username = value.get("name");
                    userEmail.setText("logged in : " + Username);
                } else {
                    Intent intent = new Intent(ContentActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        //Allowing User to Sign Out
        signout.setOnClickListener(view -> {
            mAuth.signOut();
            Intent intent = new Intent(ContentActivity.this, MainActivity.class);
            //These Flags do not allow users to Sign In again through the back button
            //Meaning a signed out user and can only sign in again through the main page
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        //Transferring user to upload page
        upload.setOnClickListener(view -> {
            Intent intent = new Intent(ContentActivity.this, UploadActivity.class);
            startActivity(intent);
        });

        //Fetching Data from Firebase and showing it on screen
        startups = new ArrayList<StartUp>();
        DatabaseReference startupsData = DB.getReference("Startups");
        startupsData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data:snapshot.getChildren()){
                    StartUp startup = data.getValue(StartUp.class);
                    startups.add(startup);
                }

                Collections.reverse(startups);
                startupsListView = findViewById(R.id.startupListView);
                arrayAdapter = new StartUpArrayAdapter(ContentActivity.this, R.layout.custom_row, startups);
                startupsListView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
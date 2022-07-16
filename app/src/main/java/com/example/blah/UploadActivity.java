package com.example.blah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
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

        StatusQuoDescription = findViewById(R.id.statusQuoText);
        StatusQuoName = findViewById(R.id.statusQuoName);
        upload = findViewById(R.id.Upload);
        cancel = findViewById(R.id.cancel);
        switchSolution = findViewById(R.id.statusQuoSwitch);

        DB = FirebaseDatabase.getInstance("https://blahblah-97064-default-rtdb.firebaseio.com/");
        DBR = DB.getReference("Users");
        DBRS = DB.getReference("Startups");


        mAuth = FirebaseAuth.getInstance();
        Fireuser = mAuth.getCurrentUser();
        String uid = Fireuser.getUid();

        DBR.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    HashMap<String, String> value = (HashMap<String, String>) task.getResult().getValue();
                    Username = value.get("name");
                }
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               createStartUp(StatusQuoName.getText().toString(),
                       StatusQuoDescription.getText().toString(),
                       Username,
                       switchSolution.isChecked());
                Intent intent = new Intent(UploadActivity.this, ContentActivity.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UploadActivity.this, ContentActivity.class);
                startActivity(intent);
            }
        });
    }

    public void createStartUp(String name, String statusQuo, String owner, boolean hasSolution) {
        StartUp startup = new StartUp(name, statusQuo, owner, hasSolution);

        DatabaseReference postsRef = DBRS;
        DatabaseReference newPostRef = postsRef.push();
        String postId = newPostRef.getKey();

        DBRS.child(postId).setValue(startup);
    }

}
package com.example.blah;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*
    This File allows the User to stay in session after exiting the App.
    The only way for a user to be out of session is through clicking sign out from main page.
*/

public class Home extends Application {
    @Override
    public void onCreate(){
        super.onCreate();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser FireUser = mAuth.getCurrentUser();

        if(FireUser != null){
            Intent intent = new Intent(Home.this, ContentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}

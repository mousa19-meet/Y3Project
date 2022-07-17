package com.example.blah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class StartUpArrayAdapter extends ArrayAdapter<StartUp> {
    private Context context;
    private int resource;


    public StartUpArrayAdapter(@NonNull Context context, int resource, ArrayList<StartUp> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(this.context).inflate(this.resource, parent, false);
        }
        // Code goes here
        StartUp startup = getItem(position);
        if (startup != null) {
            TextView nameTextView = convertView.findViewById(R.id.startupName);
            TextView statusquoTextView = convertView.findViewById(R.id.startupStatusQuo);
            TextView ownerTextView = convertView.findViewById(R.id.startupOwner);
            nameTextView.setText("*" + startup.getName());
            statusquoTextView.setText("-" + startup.getStatusQuo());
            ownerTextView.setText("Mentor : " + startup.getOwner());
        }
        return convertView;

    }
}



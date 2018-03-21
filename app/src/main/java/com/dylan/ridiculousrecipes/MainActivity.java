package com.dylan.ridiculousrecipes;


/*          1. When the app starts up, the front page of the app will be displayed. The design of the
        screen is up to you! As long as it looks reasonable, you will get full grade.
             2. Minimum requirement: have a background image that is not insulting
         and matches the theme of your app (so a photo of food or
        someone cooking should be very appropriate.) and a start button somewhere on the
        screen.
             3. Once the button is clicked, it will bring up the search activity screen.*/

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public Toolbar toolbarID;
    public Button button1ID;
    public Button button2ID;
    public Context contextID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbarID = findViewById(R.id.toolbarID);
        toolbarID.setBackgroundColor(getResources().getColor(R.color.maroon));
        toolbarID.setTitleTextColor(getResources().getColor(R.color.white));
        button1ID = findViewById(R.id.button1ID);
        button2ID = findViewById(R.id.button2ID);
        toolbarID.setTitle("Welcome to My Recipe App!");
        contextID = this;


        button1ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listIntent = new Intent(MainActivity.this, RecipeList.class);
                startActivity(listIntent);
            }
        });

        button2ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent test = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(test);
            }
        });














    }
}

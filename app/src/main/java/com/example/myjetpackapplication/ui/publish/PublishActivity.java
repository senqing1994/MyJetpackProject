package com.example.myjetpackapplication.ui.publish;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.libnavannotation.ActivityDestination;
import com.example.myjetpackapplication.R;

@ActivityDestination(pageUrl = "main/tabs/publish",needLogin = true)
public class PublishActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
    }
}
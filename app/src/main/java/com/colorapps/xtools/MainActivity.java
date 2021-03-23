package com.colorapps.xtools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences onboardEditor =
                PreferenceManager.getDefaultSharedPreferences(this);


        if (!onboardEditor.getBoolean("xtoolsWiz",false)) {
            // The user hasn't seen the OnboardingScreen yet, so show it
            startActivity(new Intent(this, WizardActivity.class));

        }else{
            startActivity(new Intent(this, ToolsActivity.class));
        }
        finish();
    }
}
package com.kiwabolab.lisa.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Diego on 19/11/2017.
 */

public class Splash extends AppCompatActivity {
    //----------------------------------------------------------------------------------------------
    //Variables

    //----------------------------------------------------------------------------------------------
    //Constructor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }
}
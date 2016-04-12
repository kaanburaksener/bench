package com.kaanburaksener.bench.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.kaanburaksener.bench.MainActivity;
import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.db.DBHandler;

/**
 * Created by kaanburaksener on 02/04/16.
 */

public class InitialActivity extends AppCompatActivity {
    private DBHandler dbHandler;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        dbHandler = new DBHandler(this);

        actionBar = getSupportActionBar();
        actionBar.hide(); //This command hides action bar

        if(dbHandler.hasUser()) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(intent);
            finish();
        }
    }

}

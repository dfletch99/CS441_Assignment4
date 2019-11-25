package com.mygdx.game;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class Leaderboard extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.backToGame);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToGame();
            }
        });
    }

    private void switchToGame() {
        Intent intent = new Intent(this, AndroidLauncher.class);
        startActivity(intent);
    }
}

//Import classes & packages
package com.example.test2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

//Define thn MainActivity klash. Kanei extend to AppCompatActivity
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the activity_main.xml layout as the UI for this activity
        setContentView(R.layout.activity_main);

        /*MenuButtonView menuButtonView = new MenuButtonView(this);
        setContentView(menuButtonView);*/

        //Dhmiourgia tou functionality tou StartGameButton
        Button startGameButton = findViewById(R.id.start_game_button);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                //Start tou GameActivity
                startActivity(intent);
            }
        });
    }
}

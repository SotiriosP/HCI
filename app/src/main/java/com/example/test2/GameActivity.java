package com.example.test2;
//Import packages & classes
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import java.util.Locale;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.util.TypedValue;
import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    //Private variables to hold UI elements
    private TextView wordTextView;
    private EditText wordEditText;
    private Button submitButton;
    private ArrayList<String> wordList;
    private int currentWordIndex = 0;

    private Handler handler;
    private TextToSpeech textToSpeech;

    private TextView timerTextView;

    private RadioButton radioButtonA, radioButtonB;
    private float normalTextSize;
    private boolean isZoomed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        // Create the MenuButtonView and add it to the layout
        /*MenuButtonView menuButtonView = new MenuButtonView(this);
        setContentView(menuButtonView);*/

        //Initialize UI elements
        wordTextView = findViewById(R.id.wordTextView);
        wordEditText = findViewById(R.id.wordEditText);
        submitButton = findViewById(R.id.submitButton);

        radioButtonA = findViewById(R.id.radioButtonA);
        radioButtonB = findViewById(R.id.radioButtonB);

        //Set the default option of radio button to A
        radioButtonA.setChecked(true);

        //Intialiaze the wordlist and shuffle it
        wordList = new ArrayList<>(Arrays.asList("cat", "dog", "water", "flower", "fire", "week", "monday", "apple", "door", "chicken", "brush", "sun", "music", "bread", "sponge", "photo", "candle", "watermelon", "pear", "carpet", "game", "school", "wall"));
        Collections.shuffle(wordList);

        // Add a timer that counts down from 5 minutes
        new CountDownTimer(5 * 60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Update the text of a TextView to show the remaining time
                TextView timerTextView = findViewById(R.id.timerTextView);
                timerTextView.setText("Time remaining: " + millisUntilFinished / 1000 + " seconds");
            }

            public void onFinish() {
                // Stop the game and redirect the user to the main menu
                Toast.makeText(GameActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }.start();


        handler = new Handler();
        // Initialize text-to-speech
        textToSpeech = new TextToSpeech(this, this);

        //Set the first word
        setWordText();

        //Set up the submit button click listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });

        //Set up the radio button group listener
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButtonA:
                        // Option A is selected
                        if (isZoomed) {
                            zoomOut();
                        }
                        break;
                    case R.id.radioButtonB:
                        // Option B is selected
                        if (!isZoomed) {
                            zoomIn();
                        }
                        break;
                }
            }
        });

        normalTextSize = wordTextView.getTextSize();


    }

    //ZoomIn function
    private void zoomIn() {
        float textSize = wordTextView.getTextSize();
        wordTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize * 3f);
        isZoomed = true;
    }

    //ZoomOut function
    private void zoomOut() {
        wordTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, normalTextSize);
        isZoomed = false;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Set the language for text-to-speech
            int result = textToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Warning message if language data is missing or the language is not supported
                Toast.makeText(getApplicationContext(), "Lang not supported.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Warning message if text-to-speech initialization failed
            Toast.makeText(getApplicationContext(), "Failed to initialize text-to-speech.", Toast.LENGTH_SHORT).show();
        }
    }

    //Dhmiourgia to setWordText function
    private void setWordText() {
        String word = wordList.get(currentWordIndex);
        char[] shuffledWord = shuffleWord(word);
        wordTextView.setText(new String(shuffledWord));
        //TextoSpeech calls the word
        textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null, null);
    }
    //Dhmiourgia tou shuffle, gia dimiourgia tou anagram
    private char[] shuffleWord(String word) {
        char[] chars = word.toCharArray();
        ArrayList<Character> charList = new ArrayList<>();
        for (char c : chars) {
            charList.add(c);
        }
        Collections.shuffle(charList);
        char[] shuffledWord = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            shuffledWord[i] = charList.get(i);
        }
        //text-to-speech
        textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null, null);
        return shuffledWord;
    }
    //Check if the word is correctly typed
    private void checkAnswer() {
        String inputWord = wordEditText.getText().toString();
        if (inputWord.equals(wordList.get(currentWordIndex))) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            currentWordIndex++;
            if (currentWordIndex == wordList.size()) {
                Toast.makeText(this, "YOU WON. CONGRATULATIONS!!!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                setWordText();
                wordEditText.setText("");
            }
        } else {
            Toast.makeText(this, "Wrong. Try again!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        // Shut down text-to-speech when the activity is destroyed to release the resources used by the engine.
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

}

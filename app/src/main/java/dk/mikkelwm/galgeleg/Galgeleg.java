package dk.mikkelwm.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Galgeleg extends AppCompatActivity {

    Button guess;
    Button endGame;
    Button newGame;

    TextView secretWord;
    TextView feedbackText;
    TextView usedLetters;
    TextView nmbrOfWrongGuesses;
    TextView gameOutcomeMsg;

    EditText editText;

    Galgelogik galgelogik;

    ImageView imageView;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galgeleg);

        galgelogik = new Galgelogik();
        editText = findViewById(R.id.editText);

        //Knapper
        newGame = findViewById(R.id.playAgain); //starter nyt spil
        endGame = findViewById(R.id.endGame); //afslutter spil
        guess = findViewById(R.id.tryGuessButton); //gætte knappen

        //tekst felter der giver brugeren feedback på gæt og progress
        secretWord = findViewById(R.id.secretWord);
        feedbackText = findViewById(R.id.guessFeedback);
        usedLetters = findViewById(R.id.usedLetters);
        nmbrOfWrongGuesses = findViewById(R.id.wrongGuesses);
        gameOutcomeMsg = findViewById(R.id.gameOutcomeMsg);

        //usynlige indtil spillet har et udfald
        newGame.setVisibility(View.INVISIBLE);
        endGame.setVisibility(View.INVISIBLE);
        gameOutcomeMsg.setVisibility(View.INVISIBLE);

        //sætter det hemmelige ord ved start
        String word = "Ordet er på "+galgelogik.getSynligtOrd().length()+" bogstaver";
        secretWord.setText(word);

        //sætter forkerte svar ved start
        String wrongAnswers = "forkerte svar: 0/7";
        nmbrOfWrongGuesses.setText(wrongAnswers);

        //sætter gæt ved start
        String lettersUsed = "Ingen gæt fortaget endnu";
        usedLetters.setText(lettersUsed);

        //Listeners
        guess.setOnClickListener(this);
        newGame.setOnClickListener(this);
        endGame.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        galgelogik.gætBogstav(editText.getText().toString()); //Sender det gættede bogstav til logikken

        guessedLetters(); //Bygger en string ud af de brugte bogstaver og sætter textfield så brugeren kan følge med.

        isGuessCorrect(); //Udskriver en besked til brugeren hvis deres gæt er korrekt/ikke korrekt

        isWinner(v); //holder øje med om brugeren har vundet eller tabt

        editText.setText(""); //sørger for at edittext bliver clearet efter hvert gæt så spilleren ikke selv skal slette bogstaver efter hver tur.

        startNewGame(v); //starter nyt spil hvis brugeren trykker "Spil igen"

        //Går til menuen
        if (v == endGame) {
            finish();
            intent = new Intent(this, MainActivity.class);
        }
    }

    private void startNewGame(View v) {
        //Kalder startNytSpil metoden
        //Nulstiller alle værdier i UI samt sætter udfalds beskeden+nytspil/afslut spil knappernes visibility
        if (v == newGame) {
            galgelogik.startNytSpil();
            secretWord.setText("Gæt igen :)");
            feedbackText.setText("");
            usedLetters.setText("");
            nmbrOfWrongGuesses.setText("");
            imageView.setImageResource(R.drawable.galge);

            newGame.setVisibility(View.INVISIBLE);
            endGame.setVisibility(View.INVISIBLE);
            gameOutcomeMsg.setVisibility(View.INVISIBLE);
        }
    }

    //bygger en string ud af liste for tidligere gæt og sætter tekst i usedLetters textview
    private void guessedLetters() {
        StringBuilder used;
        ArrayList<String> usedLetterList;
        used = new StringBuilder();
        usedLetterList = galgelogik.getBrugteBogstaver();
        for (int i = 0; i <= usedLetterList.size() - 1; i++) {
            used.append(usedLetterList.get(i)).append(", ");
            usedLetters.setText("Tidligere gæt:\n"+used);
        }
    }

    //Skjuler keyboard, Udskriver vinder/taber besked, gør nytspil/afslutspil knapperne synlige
    private void isWinner(View v) {
        if (galgelogik.erSpilletVundet()) {
            String winnerStr = "DU VANDT!";
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            gameOutcomeMsg.setVisibility(View.VISIBLE);
            newGame.setVisibility(View.VISIBLE);
            endGame.setVisibility(View.VISIBLE);
            gameOutcomeMsg.setText(winnerStr);

        } else if (galgelogik.erSpilletTabt()) {
            secretWord.setText("Ordet var: "+galgelogik.getOrdet());
            String loserString = "DU TABTE!";
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            gameOutcomeMsg.setVisibility(View.VISIBLE);
            newGame.setVisibility(View.VISIBLE);
            endGame.setVisibility(View.VISIBLE);
            gameOutcomeMsg.setText(loserString);
        }
    }

    //angiver om det bogstav der blev gættet på var korrekt/ukorrekt, hvis ukorrekt kaldes update image metoden i else statement
    private void isGuessCorrect() {
        String str,str2;
        String updateWord;
        int wrongGuesses;
        if (galgelogik.erSidsteBogstavKorrekt()) {
            str = "\"" + editText.getText() + "\"" + " var korrekt!";
            updateWord = galgelogik.getSynligtOrd();
            secretWord.setText(updateWord);
        } else {
            wrongGuesses = galgelogik.getAntalForkerteBogstaver();
            str = "\"" + editText.getText() + "\"" + " var IKKE korrekt!";
            str2 = "forkerte svar: "+wrongGuesses + "/7";
            nmbrOfWrongGuesses.setText(str2);

            imageView = findViewById(R.id.imageView);
            updateImage(wrongGuesses); //opdaterer galgen

        }
        feedbackText.setText(str);
    }

    //opdaterer galgebilledet ved forkerte svar
    public void updateImage(int wrongGuesses) {
        switch (wrongGuesses) {
            case 1:
            case 2:
                imageView.setImageResource(R.drawable.forkert1);
                break;
            case 3:
                imageView.setImageResource(R.drawable.forkert2);
                break;
            case 4:
                imageView.setImageResource(R.drawable.forkert3);
                break;
            case 5:
                imageView.setImageResource(R.drawable.forkert4);
                break;
            case 6:
                imageView.setImageResource(R.drawable.forkert5);
                break;
            case 7:
                imageView.setImageResource(R.drawable.forkert6);
                break;
            default:
                break;
        }
    }
}
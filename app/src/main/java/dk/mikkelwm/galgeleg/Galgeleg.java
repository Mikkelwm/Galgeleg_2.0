package dk.mikkelwm.galgeleg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import dk.mikkelwm.galgeleg.logik.Galgelogik;
import dk.mikkelwm.galgeleg.model.Score;

public class Galgeleg extends AppCompatActivity implements View.OnClickListener{

    Button guess;
    Button endGame;
    Button newGame;

    TextView secretWord;
    TextView feedbackText;
    TextView usedLetters;
    TextView WrongGuesses;
    TextView gameOutcomeMsg;

    EditText editText;
    Galgelogik galgelogik;
    ImageView imageView;
    Intent intent, winnerIntent,loserIntent;

    ArrayList<Score> highscoreListe = new ArrayList<>();
    String HIGHSCOREKEY2 = "highscores";
    String HIGHSCOREKEY = "highscore";

    Score hsStyring;
    String spillerNavn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galgeleg);

        galgelogik = new Galgelogik();
        hentSpillerNavn();

        editText = findViewById(R.id.editText);

        newGame = findViewById(R.id.playAgain); //start nyt spil
        endGame = findViewById(R.id.endGame); //afslut spil
        guess = findViewById(R.id.tryGuessButton); //gæt knappen

        secretWord = findViewById(R.id.secretWord);
        feedbackText = findViewById(R.id.guessFeedback);
        usedLetters = findViewById(R.id.usedLetters);
        WrongGuesses = findViewById(R.id.wrongGuesses);
        gameOutcomeMsg = findViewById(R.id.gameOutcomeMsg);

        newGame.setVisibility(View.INVISIBLE);
        endGame.setVisibility(View.INVISIBLE);
        gameOutcomeMsg.setVisibility(View.INVISIBLE);

        String word = "Ordet er på "+galgelogik.getSynligtOrd().length()+" bogstaver";
        secretWord.setText(word);

        String wrongAnswers = "forkerte svar: 0/7";
        WrongGuesses.setText(wrongAnswers);

        String lettersUsed = "Ingen gæt fortaget endnu";
        usedLetters.setText(lettersUsed);

        guess.setOnClickListener(this);
        newGame.setOnClickListener(this);
        endGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        galgelogik.gætBogstav(editText.getText().toString()); //Gættet bogstav bliver sendt til galgelogik
        guessedLetters(); //Laver string af gættede bogstaver.
        isGuessCorrect(); //Giver besked om gæt er korrekt eller ikke korrekt
        checkForWinner(v); //Checker om der er en vinder eller taber
        editText.setText(""); //sørger for at edittext bliver clearet efter hvert gæt så spilleren ikke selv skal slette bogstaver efter hver tur.
        startNewGame(v); //starter nyt spil hvis brugeren trykker "Play igen"

        if (v == endGame) {
            finish();
            intent = new Intent(this, MainActivity.class);
        }
    }

    private void startNewGame(View v) {
        if (v == newGame) {
            galgelogik.startNytSpil();
            secretWord.setText("Gæt et bogstav");
            feedbackText.setText("");
            usedLetters.setText("");
            WrongGuesses.setText("");
            imageView.setImageResource(R.drawable.galge);//Sætter galgebilledet til udgangspunkt

            newGame.setVisibility(View.INVISIBLE);//Gør Play again knappen Usynlig
            endGame.setVisibility(View.INVISIBLE);//Gør end game knappen Usynlig
            gameOutcomeMsg.setVisibility(View.INVISIBLE);//Gør Vinder/taber besked Usynlig
        }
    }

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

    private void checkForWinner(View v) {
        if (galgelogik.erSpilletVundet()) {

            String winnerStr = "DU ER EN VINDER!";//Vinder Besked
            gemInfo();//Gemmer informationer når der er en vinder
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            gameOutcomeMsg.setVisibility(View.VISIBLE);
            newGame.setVisibility(View.VISIBLE);//Gør Play again knappen synlig
            endGame.setVisibility(View.VISIBLE);//Gør end game knappen synlig
            gameOutcomeMsg.setText(winnerStr);

        } else if (galgelogik.erSpilletTabt()) {
            secretWord.setText("Ordet var: "+galgelogik.getOrd());
            String loserString = "DU ER EN TABER!"; //Taber besked
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            gameOutcomeMsg.setVisibility(View.VISIBLE);
            newGame.setVisibility(View.VISIBLE);//Gør Play again knappen synlig
            endGame.setVisibility(View.VISIBLE);//Gør end game knappen synlig
            gameOutcomeMsg.setText(loserString);
        }
    }

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
            str = "\"" + editText.getText() + "\"" + " Var IKKE korrekt!";
            str2 = "Forkerte gæt: "+wrongGuesses + "/7";
            WrongGuesses.setText(str2);

            imageView = findViewById(R.id.galgeView);
            updateImage(wrongGuesses); //Ændrer galgebilledet
        }
        feedbackText.setText(str);
    }

    public void gemInfo() {
        hentHighscore();
        hsStyring = new Score(galgelogik.getOrd(), spillerNavn, galgelogik.getAntalForkerteBogstaver() + "");
        highscoreListe.add(hsStyring);
        SharedPreferences sharedPreferences = this.getSharedPreferences(HIGHSCOREKEY2, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(highscoreListe);
        editor.putString(HIGHSCOREKEY, json);
        editor.apply();
    }

    private void hentHighscore() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(HIGHSCOREKEY2, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(HIGHSCOREKEY, null);
        Type type = new TypeToken<ArrayList<Score>>() {
        }.getType();
        highscoreListe = gson.fromJson(json, type);

        if (highscoreListe == null) {
            highscoreListe = new ArrayList<>();
        }
    }

    public void hentSpillerNavn() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Indtast spiller navn:");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                spillerNavn = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                spillerNavn = "Ikke navngivet";
                dialog.cancel();
            }
        });
        builder.show();
    }

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
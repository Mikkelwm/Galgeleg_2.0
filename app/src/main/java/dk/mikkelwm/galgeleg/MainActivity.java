package dk.mikkelwm.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button chooseNewGame, help, highscore;
    Intent newGameIntent, helpIntent, highscoreIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newGameIntent = new Intent(this, Galgeleg.class);
        helpIntent = new Intent(this, HelpMenu.class);
        highscoreIntent = new Intent(this, Highscore.class);

        chooseNewGame = findViewById(R.id.startGame);
        help = findViewById(R.id.helpButton);
        highscore = findViewById(R.id.highScore);

        chooseNewGame.setOnClickListener(this);
        help.setOnClickListener(this);
        highscore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == chooseNewGame) {
            startActivity(newGameIntent);
        } else if (v == help) {
            startActivity(helpIntent);
        } else if (v == highscore) {
            startActivity(highscoreIntent);
        }
    }
}
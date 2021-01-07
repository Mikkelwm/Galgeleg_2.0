package dk.mikkelwm.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import dk.mikkelwm.galgeleg.logik.Galgelogik;

public class Vinder extends AppCompatActivity  implements View.OnClickListener {

    Button menu;
    Intent menuIntent;
    TextView wordWas, word;
    private Galgelogik galgelogik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vinder);
        menu = findViewById(R.id.menuWinner2);

        galgelogik = Galgelogik.getInstance();
        wordWas = findViewById(R.id.winnerText);
        word = findViewById(R.id.word);
        word.setText(galgelogik.getOrd());
        try {
            winner();
        } catch (IOException e) {
            e.printStackTrace();
        }

        menuIntent = new Intent(this, MainActivity.class);
        menu.setOnClickListener(this);

    }
    private void winner() throws IOException {
        String url = "https://www.youtube.com/watch?v=CQeezCdF4mk";
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        mediaPlayer.setDataSource(url);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    @Override
    public void onClick(View v) {
        startActivity(menuIntent);
    }
}
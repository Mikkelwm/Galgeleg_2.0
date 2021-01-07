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

public class Taber extends AppCompatActivity {

    Button menu;
    TextView wordWas, word;
    private Galgelogik galgelogik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taber);

        galgelogik = Galgelogik.getInstance();
        wordWas = findViewById(R.id.loserText);
        word = findViewById(R.id.word2);
        word.setText(galgelogik.getOrd());

        try {
            loser();
        } catch (IOException e) {
            e.printStackTrace();
        }
        menu = findViewById(R.id.menuLoser);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Taber.this, MainActivity.class));
                Taber.this.finish();
            }
        });

    }

    public void loser() throws IOException {
        String url = "https://www.youtube.com/watch?v=qMhFLlCjgXg";
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
}
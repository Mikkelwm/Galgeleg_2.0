package dk.mikkelwm.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

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


        menu = findViewById(R.id.menuLoser);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Taber.this, MainActivity.class));
                Taber.this.finish();
            }
        });
    }
}
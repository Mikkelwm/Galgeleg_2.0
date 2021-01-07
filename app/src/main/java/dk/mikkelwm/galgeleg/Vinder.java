package dk.mikkelwm.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        menuIntent = new Intent(this, MainActivity.class);
        menu.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        startActivity(menuIntent);
    }
}
package dk.mikkelwm.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Vinder extends AppCompatActivity implements View.OnClickListener {
    Button menu;
    Intent menuIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vinder);
        menu = findViewById(R.id.menuWinner2);

        menuIntent = new Intent(this, MainActivity.class);
        menu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(menuIntent);
    }
}
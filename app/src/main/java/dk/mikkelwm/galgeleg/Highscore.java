package dk.mikkelwm.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dk.mikkelwm.galgeleg.model.Score;

public class Highscore extends AppCompatActivity implements View.OnClickListener{
    ListView listView;
    Button back;
    Intent intent;

    ArrayList<Score> highscoreListe;

    String HIGHSCOREKEY2 = "highscores";
    String HIGHSCOREKEY = "highscore";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        hentHighscore();
        sortHighscores();

        back = findViewById(R.id.goToMainMenu2);
        listView = findViewById(R.id.high_score_list);

        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, highscoreListe));
        back.setOnClickListener(this);
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

    private void sortHighscores() {
        hentHighscore();
        Collections.sort(highscoreListe, new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                return Integer.compare(Integer.parseInt(o1.getGaet()),Integer.parseInt(o2.getGaet()));
            }
        });
    }
    @Override
    public void onClick(View v) {
        intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
package dk.mikkelwm.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelpMenu extends AppCompatActivity {
    TextView textView;
    Button menuButton;
    Intent intent;
    String helpMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_menu);

        helpMsg = "- Spillet spilles ved at gætte et bogstav af gangen, når spilleren gætter korrekt, vil det hemmelige ord blive opdateret og bogstavet vil vises. \n\n" +
                "- Spilleren må gætte forkert 7 gange før spillet tabes\n\n" +
                "- Der vises en liste over de bogstaver der er gættet på, så spilleren ikke gætter på samme bogstav flere gange.";

        textView = findViewById(R.id.helpMsg);
        menuButton = findViewById(R.id.goToMenu);

        textView.setText(helpMsg);

        menuButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
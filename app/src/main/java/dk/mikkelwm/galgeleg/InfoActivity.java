package dk.mikkelwm.galgeleg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

public class InfoActivity extends AppCompatActivity {

    String spillerNavn, ordDerSkalGættes;
    //Spiltyper 1 = singleplayer, spiltype 2 = multiplayer
    public static int spiltype = 0;

    Intent galgelegIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        hentSpilType();

        galgelegIntent = new Intent(this, Galgeleg.class);
    }

    public void hentSpilType(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton(R.string.title_single, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Spiller har valgt Singleplayer
                //TODO ord skal vælges af random fra liste
                startActivity(galgelegIntent);
            }
        });
        builder.setNegativeButton(R.string.title_multi, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Spiller har valgt Multiplayer
                spiltype++;
                skrivOrd();
                //TODO ord skal indtastes af spiller
            }
        });

        builder.show();
        // Create the AlertDialog
        // AlertDialog dialog = builder.create();
    }

    public void skrivOrd (){
        AlertDialog.Builder wordBuilder = new AlertDialog.Builder(this);
        wordBuilder.setTitle(R.string.title_ord);
        final EditText wordInput = new EditText(this);
        wordInput.setInputType(InputType.TYPE_CLASS_TEXT);
        wordBuilder.setView(wordInput);
        wordBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO Ordet skal sættes til det indtastede
                ordDerSkalGættes = wordInput.getText().toString();
            }
        });
        wordBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Du kommer tilbage til vælg spiltype dialogen
                hentSpilType();
            }
        });
        wordBuilder.show();
    }

    /*public void hentSpillerNavn() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_navn);
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
    }*/

    public int getSpiltype(){
        return spiltype;
    }

    public String getOrdDerSkalGættes(){
        return ordDerSkalGættes;
    }
}
package dk.mikkelwm.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class VundetSpil extends AppCompatActivity implements View.OnClickListener {
    Button back;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vundet_spil);

        back.findViewById(R.id.goToMainMenu3);
        intent = new Intent(this,MainActivity.class);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(intent);
    }
}
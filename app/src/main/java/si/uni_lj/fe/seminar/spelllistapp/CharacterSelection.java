package si.uni_lj.fe.seminar.spelllistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CharacterSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_selection);

        Button button=(Button) findViewById(R.id.LogoutButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(CharacterSelection.this,Login.class);
                startActivity(intent);
            }
        });

        Button tospells=(Button) findViewById(R.id.AllSpellsButton);

        tospells.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(CharacterSelection.this,AllSpells.class);
                startActivity(intent);
            }
        });
    }
}
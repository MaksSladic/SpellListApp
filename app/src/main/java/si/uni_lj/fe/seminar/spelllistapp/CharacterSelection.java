package si.uni_lj.fe.seminar.spelllistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CharacterSelection extends AppCompatActivity {

    private String cantrips;
    private String firstLevel;
    private String secondLevel;
    private String thirdLevel;
    private String fourthLevel;
    private String fifthLevel;
    private String sixthLevel;
    private String seventhLevel;
    private String eighthLevel;
    private String ninthLevel;
    private String characters;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_selection);
        String url = "http://10.0.2.2/spell_list_app/spells/";
        String getCharactersURL = "http://10.0.2.2/spell_list_app/users/";

        String JWT = getIntent().getStringExtra("JWT");
        String UserName = getIntent().getStringExtra("UserName");

        Button button=(Button) findViewById(R.id.LogoutButton);
        Button tocharacters=(Button) findViewById((R.id.MyCharactersButton));

        tocharacters.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(CharacterSelection.this, Characters.class);
                intent.putExtra("characters",characters);
                intent.putExtra("UserName", UserName);
                intent.putExtra("cantrips", cantrips);
                intent.putExtra("firstLevel", firstLevel);
                intent.putExtra("secondLevel", secondLevel);
                intent.putExtra("thirdLevel", thirdLevel);
                intent.putExtra("fourthLevel", fourthLevel);
                intent.putExtra("fifthLevel", fifthLevel);
                intent.putExtra("sixthLevel", sixthLevel);
                intent.putExtra("seventhLevel", seventhLevel);
                intent.putExtra("eighthLevel", eighthLevel);
                intent.putExtra("ninthLevel", ninthLevel);
                startActivity(intent);
            }
        }));

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
                intent.putExtra("cantrips", cantrips);
                intent.putExtra("firstLevel", firstLevel);
                intent.putExtra("secondLevel", secondLevel);
                intent.putExtra("thirdLevel", thirdLevel);
                intent.putExtra("fourthLevel", fourthLevel);
                intent.putExtra("fifthLevel", fifthLevel);
                intent.putExtra("sixthLevel", sixthLevel);
                intent.putExtra("seventhLevel", seventhLevel);
                intent.putExtra("eighthLevel", eighthLevel);
                intent.putExtra("ninthLevel", ninthLevel);
                startActivity(intent);
            }
        });
        String characterURL = getCharactersURL + UserName;
        new GetCharacters(this).execute(characterURL);
        for (int i = 0; i < 10; i++) {
            String CallURL = "";

            CallURL = url + i;
            new GetSpells(this, i).execute(CallURL);

        }
    }

    public class GetCharacters extends AsyncTask<String, Void, String>
    {
        private CharacterSelection activity;

        public GetCharacters(CharacterSelection activity){
            this.activity=activity;
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection getcharacters;
            try {
                Log.d("try ","<--");
                URL url = new URL(strings[0]);
                getcharacters = (HttpURLConnection) url.openConnection();
                getcharacters.setRequestMethod("GET");
                getcharacters.setReadTimeout(5000);
                getcharacters.setConnectTimeout(10000);
                int status = getcharacters.getResponseCode();
                Log.d("Status: ", String.valueOf(status));
                if (status == 200){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(getcharacters.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String odgovor;
                    while((odgovor = reader.readLine()) != null){
                        response.append(odgovor);
                    }

                    return response.toString();
                }
            }
            catch (Exception e) {
                Log.d("error", String.valueOf(e));
            }
            return null;
        }
        @Override
        protected void onPostExecute(String response) {
            Log.d("Characters", String.valueOf(response));
            activity.characters = String.valueOf(response);
        }
    }

    public class GetSpells extends AsyncTask<String, Void, String>
    {
        private int level;
        private CharacterSelection activity;

        public GetSpells(CharacterSelection activity, int level) {
            this.activity = activity;
            this.level = level;
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection getspells;
            try {
                URL url = new URL(strings[0]);
                getspells = (HttpURLConnection) url.openConnection();
                getspells.setRequestMethod("GET");
                getspells.setReadTimeout(5000);
                getspells.setConnectTimeout(10000);
                int status = getspells.getResponseCode();
                if (status == 200){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(getspells.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String odgovor;
                    while((odgovor = reader.readLine()) != null){
                        response.append(odgovor);
                    }

                    return response.toString();
                }
            }
            catch (Exception e) {
                Log.d("error", String.valueOf(e));
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            Log.d("Response", String.valueOf(response));

            try {
                JSONArray spells = new JSONArray(response);

                for (int i = 0; i<spells.length();i++){
                    JSONObject object = spells.getJSONObject(i);
                }

                switch (level) {
                    case 0:
                        activity.cantrips = String.valueOf(spells);
                        break;
                    case 1:
                        activity.firstLevel = String.valueOf(spells);
                        break;
                    case 2:
                        activity.secondLevel = String.valueOf(spells);
                        break;
                    case 3:
                        activity.thirdLevel = String.valueOf(spells);
                        break;
                    case 4:
                        activity.fourthLevel = String.valueOf(spells);
                        break;
                    case 5:
                        activity.fifthLevel = String.valueOf(spells);
                        break;
                    case 6:
                        activity.sixthLevel = String.valueOf(spells);
                        break;
                    case 7:
                        activity.seventhLevel = String.valueOf(spells);
                        break;
                    case 8:
                        activity.eighthLevel = String.valueOf(spells);
                        break;
                    case 9:
                        activity.ninthLevel = String.valueOf(spells);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
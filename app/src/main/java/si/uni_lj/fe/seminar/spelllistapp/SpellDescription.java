package si.uni_lj.fe.seminar.spelllistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpellDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell_description);
        String url = "http://10.0.2.2/spell_list_app/spells/";

        String SpellName = getIntent().getStringExtra("SpellName");
        String URL = url + SpellName;
        new SpellInfo(this).execute(URL);
    }
    public class SpellInfo extends AsyncTask<String, Void, String>
    {
        private SpellDescription activity;

        public SpellInfo(SpellDescription activity)
        {
            this.activity = activity;
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection getspellinfo;
            try {
                URL url = new URL(strings[0]);
                getspellinfo = (HttpURLConnection) url.openConnection();
                getspellinfo.setRequestMethod("GET");
                getspellinfo.setReadTimeout(5000);
                getspellinfo.setConnectTimeout(10000);
                int status = getspellinfo.getResponseCode();
                if (status == 200){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(getspellinfo.getInputStream()));
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
        protected void onPostExecute(String response){
            try {
                TextView SpellName = findViewById(R.id.SpellName);
                TextView SpellLevelAndSchool = findViewById(R.id.SpellLevelAndSchool);
                TextView SpellCastingTime = findViewById(R.id.SpellCastingTime);
                TextView SpellRange =findViewById(R.id.SpellRange);
                TextView SpellComponents = findViewById(R.id.SpellComponents);
                TextView SpellDuration = findViewById(R.id.SpellDuration);
                TextView SpellDescription = findViewById(R.id.SpellDescription);

                JSONObject object = new JSONObject(response);
                Log.d("description", String.valueOf(object));
                SpellName.setText(object.getString("SpellName"));
                SpellLevelAndSchool.setText("lvl"+object.getString("SpellLevel")+" "+object.getString("SpellSchool")+" spell");
                SpellCastingTime.setText(object.getString("SpellCastingTime"));
                SpellRange.setText(object.getString("SpellRange"));
                SpellComponents.setText(object.getString("SpellComponents"));
                SpellDuration.setText(object.getString("SpellDuration"));
                SpellDescription.setText(object.getString("SpellDescription"));

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
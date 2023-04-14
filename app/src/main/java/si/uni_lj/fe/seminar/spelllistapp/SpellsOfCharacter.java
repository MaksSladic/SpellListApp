package si.uni_lj.fe.seminar.spelllistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SpellsOfCharacter extends AppCompatActivity {
    private RecyclerView cantrips,firstlevel,secondlevel,thirdlevel,fourthlevel,fifthlevel,sixthlevel,seventhlevel,eighthlevel,ninthlevel;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spells_of_character);

        String Allcantrips = getIntent().getStringExtra("cantrips");
        String AllfirstLevel = getIntent().getStringExtra("firstLevel");
        String AllsecondLevel = getIntent().getStringExtra("secondLevel");
        String AllthirdLevel = getIntent().getStringExtra("thirdLevel");
        String AllfourthLevel = getIntent().getStringExtra("fourthLevel");
        String AllfifthLevel = getIntent().getStringExtra("fifthLevel");
        String AllsixthLevel = getIntent().getStringExtra("sixthLevel");
        String AllseventhLevel = getIntent().getStringExtra("seventhLevel");
        String AlleighthLevel = getIntent().getStringExtra("eighthLevel");
        String AllninthLevel = getIntent().getStringExtra("ninthLevel");

        String UserName = getIntent().getStringExtra("UserName");
        String CharacterName = getIntent().getStringExtra("CharacterName");
        String CharacterSpells = "http://10.0.2.2/spell_list_app/characters/";
        String url = "http://10.0.2.2/spell_list_app/spells/";

        TextView CharacterNameText = findViewById(R.id.CharacterName);
        CharacterNameText.setText(CharacterName);

        cantrips = findViewById(R.id.recyclerview_spells_cantrips);
        cantrips.setLayoutManager(new LinearLayoutManager(this));

        firstlevel = findViewById(R.id.recyclerview_spells_first);
        firstlevel.setLayoutManager(new LinearLayoutManager(this));

        secondlevel = findViewById(R.id.recyclerview_spells_second);
        secondlevel.setLayoutManager(new LinearLayoutManager(this));

        thirdlevel = findViewById(R.id.recyclerview_spells_third);
        thirdlevel.setLayoutManager(new LinearLayoutManager(this));

        fourthlevel = findViewById(R.id.recyclerview_spells_fourth);
        fourthlevel.setLayoutManager(new LinearLayoutManager(this));

        fifthlevel = findViewById(R.id.recyclerview_spells_fifth);
        fifthlevel.setLayoutManager(new LinearLayoutManager(this));

        sixthlevel = findViewById(R.id.recyclerview_spells_sixth);
        sixthlevel.setLayoutManager(new LinearLayoutManager(this));

        seventhlevel = findViewById(R.id.recyclerview_spells_seventh);
        seventhlevel.setLayoutManager(new LinearLayoutManager(this));

        eighthlevel = findViewById(R.id.recyclerview_spells_eighth);
        eighthlevel.setLayoutManager(new LinearLayoutManager(this));

        ninthlevel = findViewById(R.id.recyclerview_spells_ninth);
        ninthlevel.setLayoutManager(new LinearLayoutManager(this));

        String URL = CharacterSpells + CharacterName + "/" + UserName;
        String AllSpellsOfLevel = "";
        for (int i = 0; i < 10; i++) {
            switch (i) {
                case 0:
                    AllSpellsOfLevel = Allcantrips;
                    break;
                case 1:
                    AllSpellsOfLevel = AllfirstLevel;
                    break;
                case 2:
                    AllSpellsOfLevel = AllsecondLevel;
                    break;
                case 3:
                    AllSpellsOfLevel = AllthirdLevel;
                    break;
                case 4:
                    AllSpellsOfLevel = AllfourthLevel;
                    break;
                case 5:
                    AllSpellsOfLevel = AllfifthLevel;
                    break;
                case 6:
                    AllSpellsOfLevel = AllsixthLevel;
                    break;
                case 7:
                    AllSpellsOfLevel = AllseventhLevel;
                    break;
                case 8:
                    AllSpellsOfLevel = AlleighthLevel;
                    break;
                case 9:
                    AllSpellsOfLevel = AllninthLevel;
                    break;
            }
            new GetCharactersSpells(this,i, AllSpellsOfLevel).execute(URL);
        }
    }



    public class CustomAdapter extends RecyclerView.Adapter<SpellsOfCharacter.CustomAdapter.SpellInfo>
    {
        private Context context;
        private String string;
        private List list;

        public class SpellInfo extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            public TextView SpellName, CastingTime, Components, SpellSchool, Ritual, Concentration;
            public SpellInfo(@NonNull View itemView) {
                super(itemView);
                SpellName = itemView.findViewById(R.id.textview_spell_name);
                CastingTime = itemView.findViewById(R.id.CastingTime);
                Components = itemView.findViewById(R.id.Spell_Components);
                SpellSchool = itemView.findViewById(R.id.SpellSchool);
                Ritual = itemView.findViewById(R.id.Ritual);
                Concentration = itemView.findViewById(R.id.Concentration);
            }

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                Log.d("position", String.valueOf(position));
                if (position != RecyclerView.NO_POSITION) {
                    JSONObject object = (JSONObject) list.get(position);

                    try {
                        String SpellName = object.getString("SpellName");
                        Intent intent= new Intent(SpellsOfCharacter.this, SpellDescription.class);
                        intent.putExtra("SpellName",SpellName);
                        startActivity(intent);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        public CustomAdapter(Context context, String string)
        {
            this.context = context;
            this.string = string;
        }
        @NonNull
        @Override
        public CustomAdapter.SpellInfo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SpellsOfCharacter.CustomAdapter.SpellInfo(LayoutInflater.from(context).inflate(R.layout.recyclerview_item_spell, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CustomAdapter.SpellInfo holder, int position) {
            JSONArray spells = null;
            try {
                spells = new JSONArray(string);
                list = new ArrayList<>();
                for (int i = 0; i < spells.length(); i++) {
                    JSONObject object = spells.getJSONObject(i);
                    list.add(object);
                }

                JSONObject object = (JSONObject) list.get(position);
                holder.SpellName.setText(object.getString("SpellName"));
                holder.CastingTime.setText(object.getString("SpellCastingTime"));
                String Components = object.getString("SpellComponents");
                int index = Components.indexOf("(");
                if (index != -1) {
                    String result = Components.substring(0, index);
                    holder.Components.setText(result);
                } else {
                    holder.Components.setText(object.getString("SpellComponents"));
                }
                holder.SpellSchool.setText(object.getString("SpellSchool"));
                holder.Ritual.setText(object.getString("SpellRitual"));
                holder.Concentration.setText(object.getString("SpellConcentration"));


            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public int getItemCount() {
            if (string == null) {
                return 0;
            }
            try {
                JSONArray spells = new JSONArray(string);
                return spells.length();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*public class AllSpells extends AsyncTask<String, Void, String>
    {
        private SpellsOfCharacter activity;
        private int level;

        public AllSpells(SpellsOfCharacter activity, int level){
            this.activity = activity;
            this.level = level;
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection getallspells;
            try {
                URL url = new URL(strings[0]);
                getallspells = (HttpURLConnection) url.openConnection();
                getallspells.setRequestMethod("GET");
                getallspells.setReadTimeout(5000);
                getallspells.setConnectTimeout(10000);
                int status = getallspells.getResponseCode();
                if (status == 200){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(getallspells.getInputStream()));
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
        protected void onPostExecute(String response){
            try {
                JSONArray spells = new JSONArray(response);

                for (int i = 0; i<spells.length();i++){
                    JSONObject object = spells.getJSONObject(i);
                }

                switch (level) {
                    case 0:
                        activity.AllCantrips = String.valueOf(spells);
                        break;
                    case 1:
                        activity.AllFirstLevel = String.valueOf(spells);
                        break;
                    case 2:
                        activity.AllSecondLevel = String.valueOf(spells);
                        break;
                    case 3:
                        activity.AllThirdLevel = String.valueOf(spells);
                        break;
                    case 4:
                        activity.AllFourthLevel = String.valueOf(spells);
                        break;
                    case 5:
                        activity.AllFifthLevel = String.valueOf(spells);
                        break;
                    case 6:
                        activity.AllSixthLevel = String.valueOf(spells);
                        break;
                    case 7:
                        activity.AllSeventhLevel = String.valueOf(spells);
                        break;
                    case 8:
                        activity.AllEighthLevel = String.valueOf(spells);
                        break;
                    case 9:
                        activity.AllNinthLevel = String.valueOf(spells);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }*/

    public class GetCharactersSpells extends AsyncTask<String, Void, String>
    {
        private SpellsOfCharacter activity;
        private int level;
        private String AllSpellsOfLevel;

        public GetCharactersSpells(SpellsOfCharacter activity, int level, String AllSpellsOfLevel) {
            this.activity = activity;
            this.level = level;
            this.AllSpellsOfLevel = AllSpellsOfLevel;

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
                list = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(response);
                JSONArray Spels = jsonArray.getJSONArray(1);
                for (int i = 0; i<Spels.length();i++){
                    Log.d("Spell Name",Spels.getString(i));
                    list.add(Spels.getString(i));
                }
                Log.d("List:", String.valueOf(list));
                Log.d("array",AllSpellsOfLevel);

                JSONArray spells = new JSONArray(AllSpellsOfLevel);
                JSONArray SpellsToDisplay = new JSONArray();

                for (int i = 0; i<spells.length();i++){
                    JSONObject object = spells.getJSONObject(i);
                    String spellName = object.getString("SpellName");
                    if(list.contains(spellName))
                    {
                        SpellsToDisplay.put(object);
                    }
                }
                Log.d("to display:", String.valueOf(SpellsToDisplay));
                switch (level){
                    case 0:
                        SpellsOfCharacter.CustomAdapter customadapter0 = new SpellsOfCharacter.CustomAdapter(SpellsOfCharacter.this,String.valueOf(SpellsToDisplay));
                        cantrips.setAdapter(customadapter0);
                        break;
                    case 1:
                        SpellsOfCharacter.CustomAdapter customadapter1 = new SpellsOfCharacter.CustomAdapter(SpellsOfCharacter.this,String.valueOf(SpellsToDisplay));
                        firstlevel.setAdapter(customadapter1);
                        break;
                    case 2:
                        SpellsOfCharacter.CustomAdapter customadapter2 = new SpellsOfCharacter.CustomAdapter(SpellsOfCharacter.this,String.valueOf(SpellsToDisplay));
                        secondlevel.setAdapter(customadapter2);
                        break;
                    case 3:
                        SpellsOfCharacter.CustomAdapter customadapter3 = new SpellsOfCharacter.CustomAdapter(SpellsOfCharacter.this, String.valueOf(SpellsToDisplay));
                        thirdlevel.setAdapter(customadapter3);
                        break;
                    case 4:
                        SpellsOfCharacter.CustomAdapter customadapter4 = new SpellsOfCharacter.CustomAdapter(SpellsOfCharacter.this, String.valueOf(SpellsToDisplay));
                        fourthlevel.setAdapter(customadapter4);
                        break;
                    case 5:
                        SpellsOfCharacter.CustomAdapter customadapter5 = new SpellsOfCharacter.CustomAdapter(SpellsOfCharacter.this, String.valueOf(SpellsToDisplay));
                        fifthlevel.setAdapter(customadapter5);
                        break;
                    case 6:
                        SpellsOfCharacter.CustomAdapter customadapter6 = new SpellsOfCharacter.CustomAdapter(SpellsOfCharacter.this, String.valueOf(SpellsToDisplay));
                        sixthlevel.setAdapter(customadapter6);
                        break;
                    case 7:
                        SpellsOfCharacter.CustomAdapter customadapter7 = new SpellsOfCharacter.CustomAdapter(SpellsOfCharacter.this, String.valueOf(SpellsToDisplay));
                        seventhlevel.setAdapter(customadapter7);
                        break;
                    case 8:
                        SpellsOfCharacter.CustomAdapter customadapter8 = new SpellsOfCharacter.CustomAdapter(SpellsOfCharacter.this, String.valueOf(SpellsToDisplay));
                        eighthlevel.setAdapter(customadapter8);
                        break;
                    case 9:
                        SpellsOfCharacter.CustomAdapter customadapter9 = new SpellsOfCharacter.CustomAdapter(SpellsOfCharacter.this, String.valueOf(SpellsToDisplay));
                        ninthlevel.setAdapter(customadapter9);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
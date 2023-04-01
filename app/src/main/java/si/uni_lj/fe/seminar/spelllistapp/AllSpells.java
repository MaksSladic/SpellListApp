package si.uni_lj.fe.seminar.spelllistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class AllSpells extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_spells);
        String url = "http://10.0.2.2/spell_list_app/spells/";


        for(int i=0;i<10;i++)
        {
            String CallURL = "";
            switch (i){
                case 0:
                    recyclerView = findViewById(R.id.recyclerview_spells_cantrips);
                    recyclerView.setLayoutManager(new LinearLayoutManager(AllSpells.this));
                    CallURL = url + i;
                    new GetSpells().execute(CallURL);
                    CustomAdapter customadapter= new CustomAdapter(AllSpells.this, GetSpells);
                    recyclerView.setAdapter(customadapter);
                    break;
                case 1:
                    recyclerView = findViewById(R.id.recyclerview_spells_first);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    CallURL = url + i;
                    new GetSpells().execute(CallURL);
                    break;
                case 2:
                    recyclerView = findViewById(R.id.recyclerview_spells_second);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    CallURL = url + i;
                    new GetSpells().execute(CallURL);
                    break;
                case 3:
                    recyclerView = findViewById(R.id.recyclerview_spells_third);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    CallURL = url + i;
                    new GetSpells().execute(CallURL);
                    break;
                case 4:
                    recyclerView = findViewById(R.id.recyclerview_spells_fourth);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    CallURL = url + i;
                    new GetSpells().execute(CallURL);
                    break;
                case 5:
                    recyclerView = findViewById(R.id.recyclerview_spells_fifth);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    CallURL = url + i;
                    new GetSpells().execute(CallURL);
                    break;
                case 6:
                    recyclerView = findViewById(R.id.recyclerview_spells_sixth);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    CallURL = url + i;
                    new GetSpells().execute(CallURL);
                    break;
                case 7:
                    recyclerView = findViewById(R.id.recyclerview_spells_seventh);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    CallURL = url + i;
                    new GetSpells().execute(CallURL);
                    break;
                case 8:
                    recyclerView = findViewById(R.id.recyclerview_spells_eighth);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    CallURL = url + i;
                    new GetSpells().execute(CallURL);
                    break;
                case 9:
                    recyclerView = findViewById(R.id.recyclerview_spells_ninth);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    CallURL = url + i;
                    new GetSpells().execute(CallURL);
                    break;
            }
        }

    }
    public class GetSpells extends AsyncTask<String, Void, String>
    {

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
                Log.d("Spells: ", String.valueOf(spells));
                for (int i = 0; i<spells.length();i++){
                    JSONObject object = spells.getJSONObject(i);
                    Log.d("Spell Name",object.getString("SpellName"));


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class SpellInfo extends RecyclerView.ViewHolder {
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
    }

    public class CustomAdapter extends RecyclerView.Adapter<SpellInfo>{
        private Context context;
        private String string;

        public CustomAdapter(Context context, String string) {
            this.context = context;
            this.string = string;
        }

        @NonNull
        @Override
        public SpellInfo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SpellInfo(LayoutInflater.from(context).inflate(R.layout.recyclerview_item_spell, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull SpellInfo holder, int position) {
            JSONArray spells = null;
            List list;
            try {
                Log.d("Tukej ","tukej");
                spells = new JSONArray(string);
                list = new ArrayList<>();
                for (int i = 0; i < spells.length(); i++) {
                    JSONObject object = spells.getJSONObject(i);
                    list.add(object);
                }

                JSONObject object = (JSONObject) list.get(position);
                holder.SpellName.setText(object.getString("SpellName"));

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

}
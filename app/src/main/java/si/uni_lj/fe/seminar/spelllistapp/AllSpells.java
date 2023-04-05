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

    private RecyclerView recyclerView0,recyclerView1,recyclerView2,recyclerView3,recyclerView4,recyclerView5,recyclerView6,recyclerView7,recyclerView8,recyclerView9;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_spells);
        String url = "http://10.0.2.2/spell_list_app/spells/";


        String cantrips = getIntent().getStringExtra("cantrips");
        String firstLevel = getIntent().getStringExtra("firstLevel");
        String secondLevel = getIntent().getStringExtra("secondLevel");
//        String cantrips = getIntent().getStringExtra("cantrips");
//        String cantrips = getIntent().getStringExtra("cantrips");
//        String cantrips = getIntent().getStringExtra("cantrips");
//        String cantrips = getIntent().getStringExtra("cantrips");
//        String cantrips = getIntent().getStringExtra("cantrips");
        String ninthLevel = getIntent().getStringExtra("ninthLevel");


        CustomAdapter customadapter0 = new CustomAdapter(this,cantrips);
        CustomAdapter customadapter1 = new CustomAdapter(this,firstLevel);
        recyclerView0 = findViewById(R.id.recyclerview_spells_cantrips);
        recyclerView1 = findViewById(R.id.recyclerview_spells_first);

        recyclerView0.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));

        recyclerView0.setAdapter(customadapter0);
        recyclerView1.setAdapter(customadapter1);


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

        private List list;

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
            try {
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
            if (string == null) {
                return 0;
            }
            try {
                JSONArray spells = new JSONArray(string);
                return spells.length();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
//            return list.size();
        }
    }

}
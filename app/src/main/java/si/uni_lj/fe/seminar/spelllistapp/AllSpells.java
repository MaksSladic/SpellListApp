package si.uni_lj.fe.seminar.spelllistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_spells);
        String url = "http://10.0.2.2/spell_list_app/spells/";


        String cantrips = getIntent().getStringExtra("cantrips");
        String firstLevel = getIntent().getStringExtra("firstLevel");
        String secondLevel = getIntent().getStringExtra("secondLevel");
        String thirdLevel = getIntent().getStringExtra("thirdLevel");
        String fourthLevel = getIntent().getStringExtra("fourthLevel");
        String fifthLevel = getIntent().getStringExtra("fifthLevel");
        String sixthLevel = getIntent().getStringExtra("sixthLevel");
        String seventhLevel = getIntent().getStringExtra("seventhLevel");
        String eighthLevel = getIntent().getStringExtra("eighthLevel");
        String ninthLevel = getIntent().getStringExtra("ninthLevel");


        CustomAdapter customadapter0 = new CustomAdapter(this,cantrips);
        CustomAdapter customadapter1 = new CustomAdapter(this,firstLevel);
        CustomAdapter customadapter2 = new CustomAdapter(this,secondLevel);
        CustomAdapter customadapter3 = new CustomAdapter(this,thirdLevel);
        CustomAdapter customadapter4 = new CustomAdapter(this,fourthLevel);
        CustomAdapter customadapter5 = new CustomAdapter(this,fifthLevel);
        CustomAdapter customadapter6 = new CustomAdapter(this,sixthLevel);
        CustomAdapter customadapter7 = new CustomAdapter(this,seventhLevel);
        CustomAdapter customadapter8 = new CustomAdapter(this,eighthLevel);
        CustomAdapter customadapter9 = new CustomAdapter(this,ninthLevel);

        recyclerView0 = findViewById(R.id.recyclerview_spells_cantrips);
        recyclerView1 = findViewById(R.id.recyclerview_spells_first);
        recyclerView2 = findViewById(R.id.recyclerview_spells_second);
        recyclerView3 = findViewById(R.id.recyclerview_spells_third);
        recyclerView4 = findViewById(R.id.recyclerview_spells_fourth);
        recyclerView5 = findViewById(R.id.recyclerview_spells_fifth);
        recyclerView6 = findViewById(R.id.recyclerview_spells_sixth);
        recyclerView7 = findViewById(R.id.recyclerview_spells_seventh);
        recyclerView8 = findViewById(R.id.recyclerview_spells_eighth);
        recyclerView9 = findViewById(R.id.recyclerview_spells_ninth);

        recyclerView0.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        recyclerView4.setLayoutManager(new LinearLayoutManager(this));
        recyclerView5.setLayoutManager(new LinearLayoutManager(this));
        recyclerView6.setLayoutManager(new LinearLayoutManager(this));
        recyclerView7.setLayoutManager(new LinearLayoutManager(this));
        recyclerView8.setLayoutManager(new LinearLayoutManager(this));
        recyclerView9.setLayoutManager(new LinearLayoutManager(this));

        recyclerView0.setAdapter(customadapter0);
        recyclerView1.setAdapter(customadapter1);
        recyclerView2.setAdapter(customadapter2);
        recyclerView3.setAdapter(customadapter3);
        recyclerView4.setAdapter(customadapter4);
        recyclerView5.setAdapter(customadapter5);
        recyclerView6.setAdapter(customadapter6);
        recyclerView7.setAdapter(customadapter7);
        recyclerView8.setAdapter(customadapter8);
        recyclerView9.setAdapter(customadapter9);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public class CustomAdapter extends RecyclerView.Adapter<AllSpells.CustomAdapter.SpellInfo>{
        private Context context;
        private String string;

        private List list;

        public class SpellInfo extends RecyclerView.ViewHolder implements View.OnClickListener{
            public TextView SpellName, CastingTime, Components, SpellSchool, Ritual, Concentration;
            public SpellInfo(@NonNull View itemView) {
                super(itemView);
                SpellName = itemView.findViewById(R.id.textview_spell_name);
                CastingTime = itemView.findViewById(R.id.CastingTime);
                Components = itemView.findViewById(R.id.Spell_Components);
                SpellSchool = itemView.findViewById(R.id.SpellSchool);
                Ritual = itemView.findViewById(R.id.Ritual);
                Concentration = itemView.findViewById(R.id.Concentration);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                Log.d("position", String.valueOf(position));
                if (position != RecyclerView.NO_POSITION) {
                    JSONObject object = (JSONObject) list.get(position);

                    try {
                        String SpellName = object.getString("SpellName");
                        Intent intent = new Intent(AllSpells.this, SpellDescription.class);
                        intent.putExtra("SpellName", SpellName);
                        startActivity(intent);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

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

}
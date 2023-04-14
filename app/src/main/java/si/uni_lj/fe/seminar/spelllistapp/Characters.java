package si.uni_lj.fe.seminar.spelllistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import java.util.ArrayList;
import java.util.List;

public class Characters extends AppCompatActivity {

    private RecyclerView recyclerview_characters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);

        String UserName = getIntent().getStringExtra("UserName");
        String Characters = getIntent().getStringExtra("characters");
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
//        Log.d("characters",Characters);


        Characters.CustomAdapter customadapter = new Characters.CustomAdapter(this,Characters, UserName, cantrips,firstLevel,secondLevel,thirdLevel,fourthLevel,fifthLevel,sixthLevel,seventhLevel,eighthLevel,ninthLevel);

        recyclerview_characters = findViewById(R.id.recyclerview_characters);
        recyclerview_characters.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_characters.setAdapter(customadapter);

    }


    public class CustomAdapter extends RecyclerView.Adapter<Characters.CustomAdapter.CharacterInfo>{
        private Context context;
        private String string;
        private String UserName;
        private String cantrips,firstLevel,secondLevel,thirdLevel,fourthLevel,fifthLevel,sixthLevel,seventhLevel,eighthLevel,ninthLevel;
        private Object element;
        private List list;

        public CustomAdapter(Context context, String string, String UserName, String cantrips, String firstLevel, String secondLevel, String thirdLevel, String fourthLevel, String fifthLevel, String sixthLevel, String seventhLevel, String eighthLevel, String ninthLevel) {
            this.context = context;
            this.string = string;
            this.UserName = UserName;
            this.cantrips = cantrips;
            this.firstLevel = firstLevel;
            this.secondLevel = secondLevel;
            this.thirdLevel = thirdLevel;
            this.fourthLevel = fourthLevel;
            this.fifthLevel = fifthLevel;
            this.sixthLevel = sixthLevel;
            this.seventhLevel = seventhLevel;
            this.eighthLevel = eighthLevel;
            this.ninthLevel = ninthLevel;
        }

        public class CharacterInfo extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView CharacterName;
            public CharacterInfo(@NonNull View itemView) {
                super(itemView);
                CharacterName = itemView.findViewById(R.id.textview_characterList_name);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    JSONObject object = (JSONObject) list.get(position);

                    try {
                        String characterName = object.getString("CharacterName");
                        Intent intent= new Intent(Characters.this, SpellsOfCharacter.class);
                        intent.putExtra("CharacterName",characterName);
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
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }

        @NonNull
        @Override
        public Characters.CustomAdapter.CharacterInfo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Characters.CustomAdapter.CharacterInfo(LayoutInflater.from(context).inflate(R.layout.recyclerview_item_character, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull Characters.CustomAdapter.CharacterInfo holder, int position) {
            JSONArray characters = null;
            try {
                characters = new JSONArray(string);
                list = new ArrayList<>();
                element = characters.opt(1);
                if(!(element instanceof String))
                {
                    for (int i = 1; i < characters.length(); i++) {
                        JSONObject object = characters.getJSONObject(i);
                        list.add(object);
                    }

                    JSONObject object = (JSONObject) list.get((position));
                    holder.CharacterName.setText(object.getString("CharacterName"));
                }
                else
                {
                    TextView NoCharactersText = ((Characters) holder.itemView.getContext()).findViewById(R.id.NoCharactersText);
                    NoCharactersText.setText("You have no characters. Create them in the web page");
                }


            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public int getItemCount() {
            if (string == null || element instanceof String) {
                return 0;
            }
            try {
                JSONArray characters = new JSONArray(string);
                return characters.length()-1;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
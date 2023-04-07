package si.uni_lj.fe.seminar.spelllistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        Characters.CustomAdapter customadapter = new Characters.CustomAdapter(this,Characters);

        recyclerview_characters = findViewById(R.id.recyclerview_characters);
        recyclerview_characters.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_characters.setAdapter(customadapter);
    }
    public class CharacterInfo extends RecyclerView.ViewHolder {
        public TextView CharacterName;
        public CharacterInfo(@NonNull View itemView) {
            super(itemView);
            CharacterName = itemView.findViewById(R.id.textview_characterList_name);
        }
    }
    public class CustomAdapter extends RecyclerView.Adapter<Characters.CharacterInfo>{
        private Context context;
        private String string;

        private List list;

        public CustomAdapter(Context context, String string) {
            this.context = context;
            this.string = string;
        }

        @NonNull
        @Override
        public Characters.CharacterInfo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Characters.CharacterInfo(LayoutInflater.from(context).inflate(R.layout.recyclerview_item_character, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull Characters.CharacterInfo holder, int position) {
            JSONArray characters = null;
            try {
                characters = new JSONArray(string);
                list = new ArrayList<>();
                if(String.valueOf(characters.getJSONObject(1)) != "No characters have been created")
                {
                    for (int i = 1; i < characters.length(); i++) {
                        JSONObject object = characters.getJSONObject(i);
                        list.add(object);
                    }

                    JSONObject object = (JSONObject) list.get((position));
                    holder.CharacterName.setText(object.getString("CharacterName"));
                }


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
                JSONArray characters = new JSONArray(string);
                return characters.length()-1;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
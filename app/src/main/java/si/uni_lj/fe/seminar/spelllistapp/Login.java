package si.uni_lj.fe.seminar.spelllistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usernameEditText = findViewById(R.id.usernameText);
        EditText passwordEditText = findViewById(R.id.passwordText);

        String url = "http://10.0.2.2/spell_list_app/login";

        Button button=(Button) findViewById(R.id.LoginButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = usernameEditText.getText().toString();
                String Password = passwordEditText.getText().toString();

                new LoginUser(Login.this, Username, Password).execute(url);


            }
        });
    }

    public class LoginUser extends AsyncTask<String, Void, String>
    {
        private String Username;
        private String Password;
        private Login activity;

        public LoginUser(Login activity, String Username, String Password){
            this.activity = (Login) activity;
            this.Username = Username;
            this.Password = Password;
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection loginuser;
            try {
                URL url = new URL(strings[0]);
                loginuser = (HttpURLConnection) url.openConnection();
                loginuser.setRequestMethod("POST");
                loginuser.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                loginuser.setReadTimeout(5000);
                loginuser.setConnectTimeout(10000);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Username", Username);
                jsonObject.put("password", Password);

                String requestBody = jsonObject.toString();

                loginuser.setDoOutput(true);

                OutputStream outputStream = loginuser.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                outputStreamWriter.write(requestBody);
                outputStreamWriter.flush();
                outputStreamWriter.close();

                Log.d("Tukej: ","Tukej1");
                int status = loginuser.getResponseCode();
                Log.d("status: ", String.valueOf(status));
                if (status == 200){
                    Log.d("Tukej: ","Tukej2");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(loginuser.getInputStream()));
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

            Intent intent= new Intent(Login.this,CharacterSelection.class);
            intent.putExtra("JWT",String.valueOf(response));
            intent.putExtra("UserName", Username);
            startActivity(intent);
        }
    }

}
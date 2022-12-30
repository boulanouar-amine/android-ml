package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn;

    //Read the data from the csv file and add it to the database
    private void readPersonneData(DatabaseHandler db) {
        List<String> feautures;
        InputStream is = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );
        String line = "";
        try {
            //skip the headers
            feautures = List.of(reader.readLine().split(","));

            while ((line = reader.readLine()) != null) {
                //split by ','
                String[] tokens = line.split(",");
                Personne personne = new Personne();

                personne.setAge(Integer.parseInt(tokens[0]));
                personne.setGenre(tokens[1]);
                personne.setBloodPressure(tokens[2]);
                personne.setCholesterol(tokens[3]);
                personne.setNa(Double.parseDouble(tokens[4]));
                personne.setK(Double.parseDouble(tokens[5]));
                personne.setDrug(tokens[6]);

                db.addPersonne(personne);
            }
        } catch (IOException e) {
            Log.wtf("Application", "Error reading data file on line " + line, e);
        }

    }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            //Handeling the database on first launch
            DatabaseHandler db = new DatabaseHandler(this);

            readPersonneData(db);
            //i have just one user currently but i will add more later
            User admin = new User("admin","admin");
            db.addUser(admin);



        setContentView(R.layout.activity_main);

            TextView signup = (TextView) findViewById(R.id.signup);
        btn = (Button) findViewById(R.id.button);
            EditText username = (EditText) findViewById(R.id.username);
            EditText password = (EditText) findViewById(R.id.password);
        btn.setOnClickListener(view -> {
            User loginUser = new User(username.getText().toString(),password.getText().toString());
            if (db.checkUsernamePassword(loginUser))
            {
                Toast.makeText(MainActivity.this, "logged in as " +loginUser.getUsername(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Activityform.class));
            } else {
                Toast.makeText(MainActivity.this, "wrong username or password", Toast.LENGTH_SHORT).show();
            }
        });

            signup.setOnClickListener(
                    v -> {
                        Intent intent = new Intent(getApplicationContext(),MainActivitysingup.class);
                        startActivity(intent);
                    }
            );
    }
}
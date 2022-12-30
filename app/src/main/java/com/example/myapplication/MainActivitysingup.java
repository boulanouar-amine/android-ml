package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivitysingup extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitysingup);


        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        DatabaseHandler db = new DatabaseHandler(this);

        TextView login = (TextView) findViewById(R.id.login);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(view -> {
            User loginUser = new User(username.getText().toString(),password.getText().toString());
            db.addUser(loginUser);

            Intent intent = new Intent(getApplicationContext(),Activityform.class);
            startActivity(intent);
            finish();
        });
        login.setOnClickListener(
                v -> {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
        );

    }
}
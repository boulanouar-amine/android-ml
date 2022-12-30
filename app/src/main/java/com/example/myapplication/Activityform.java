package com.example.myapplication;

import androidx.activity.result.ActivityResultCaller;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Objects;

public class Activityform extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        //text fields
        EditText Age = (EditText) findViewById(R.id.Age);

        //TOOD need to handle normalisation of the imputs between 1 and 0
        EditText Na = (EditText) findViewById(R.id.Na);
        EditText k = (EditText) findViewById(R.id.K);

        //radio Groups
        RadioGroup radioGenre = (RadioGroup) findViewById(R.id.radio_Genre);
        RadioGroup radioBloodPressure = (RadioGroup) findViewById(R.id.radio_BloodPressure);
        RadioGroup radioCholesterol = (RadioGroup) findViewById(R.id.radio_Cholesterol);

        //Button
        Button submit = (Button) findViewById(R.id.submit);

        DatabaseHandler db = new DatabaseHandler(this);


        submit.setOnClickListener(view -> {

            //getting the values from the radio fields
            String genre = radioGenericHandler(radioGenre,"Please select a Genre");

            if (Objects.equals(genre, "MALE")) genre = "M";
            else if(Objects.equals(genre, "FEMALE")) genre = "F";

            String bloodPressure = radioGenericHandler(radioBloodPressure,"Please select your blood pressure");
            String cholesterol = radioGenericHandler(radioCholesterol,"Please select your cholesterol");

            //this has a quirk of showing the RadioGroup error message before the EditText error message
            if(isNotEmpty(Age,"Please enter your Age")
                    && isNotEmpty(Na,"Please enter your Na")
                    && isNotEmpty(k,"Please enter your K")
                    && !Objects.equals(genre, "")
                    && !Objects.equals(bloodPressure, "")
                    && !Objects.equals(cholesterol, ""))
            {
                Personne p = new Personne();

                p.setAge(Integer.parseInt(Age.getText().toString()));
                p.setGenre(genre);
                p.setCholesterol(cholesterol);
                p.setBloodPressure(bloodPressure);
                p.setNa(Double.parseDouble(Na.getText().toString()));
                p.setK(Double.parseDouble(k.getText().toString()));
//not using the database
//                db.addPersonne(p);
                Toast.makeText(this, "Choose your Algorithm", Toast.LENGTH_SHORT).show();
                Intent algorithmIntent = new Intent(Activityform.this, ActivityAlgoritm_choice.class);
                algorithmIntent.putExtra("PAge", p.getAge());
                algorithmIntent.putExtra("PGenre", genre);
                algorithmIntent.putExtra("PCholesterol", cholesterol);
                algorithmIntent.putExtra("PBloodPressure", bloodPressure);
                algorithmIntent.putExtra("PNa",p.getNa());
                algorithmIntent.putExtra("PK", p.getK());


                startActivity(algorithmIntent);
            }
        });
    }

    private boolean isNotEmpty(EditText editText , String errorMessage) {
        if(editText.getText().toString().trim().length() == 0){
            Toast.makeText(Activityform.this, errorMessage, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private String radioGenericHandler(RadioGroup radioGroup,String errorMessage){
        try {
            RadioButton selectedRadioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
            return selectedRadioButton.getText().toString().toUpperCase();
        } catch (NullPointerException e) {
            Toast.makeText(Activityform.this, errorMessage, Toast.LENGTH_SHORT).show();
            return "";
        }
    }

}


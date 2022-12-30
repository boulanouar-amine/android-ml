package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ml.bayes.NaiveBayes;
import com.example.myapplication.ml.dt.DecisionTree;
import com.example.myapplication.ml.knn.KNN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ActivityAlgoritm_choice extends AppCompatActivity {

    //Read the data from the csv file and add it to the database
    private List<String> readPersonneData() {

        List<String> features = new ArrayList<>();

        InputStream is = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );

        String line = "";

        try {
            //skip the headers
            features = List.of(reader.readLine().split(","));
            return features;
        } catch (IOException e) {
            Log.wtf("Application", "Error reading data file on line " + line, e);
        }

        return features;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algoritm_choice);

        DatabaseHandler db = new DatabaseHandler(this);

        Personne targetPersonne = new Personne();
        targetPersonne.setAge(getIntent().getIntExtra("PAge", 0));
        targetPersonne.setGenre(getIntent().getStringExtra("PGenre"));
        targetPersonne.setCholesterol(getIntent().getStringExtra("PCholesterol"));
        targetPersonne.setBloodPressure(getIntent().getStringExtra("PBloodPressure"));
        targetPersonne.setNa(getIntent().getDoubleExtra("PNa", 0));
        targetPersonne.setK(getIntent().getDoubleExtra("PK", 0));


        Button calculate = (Button) findViewById(R.id.calculate);
        RadioGroup radioAlgo = (RadioGroup) findViewById(R.id.radio_Choice);

        LinearLayout linearresult = (LinearLayout) findViewById(R.id.linearresult);
        TextView result = (TextView) findViewById(R.id.result);

        calculate.setOnClickListener(view -> {
            try {
                RadioButton selectedAlgo = (RadioButton) findViewById(radioAlgo.getCheckedRadioButtonId());
                String selectedAlgoText = selectedAlgo.getText().toString();

                //getting the last added person in the database from their ROWID instead of sending the whole object from the previous activity
//                Personne targetPersonne =  db.getLastAddedPersonne();

                //helps having same name for the results of all the algorithms instead of having to change the name of the result for each algorithm
                String drugResult = "";

                List<List<String>> personneList = new ArrayList<>();
                List<String> targetList = new ArrayList<>();
                for (Personne p : db.getAllPersonnes()) {
                    personneList.add(p.toList());
                    if (p.getDrug()!= null && !p.getDrug().isEmpty() && !p.getDrug().equals("null"))
                        targetList.add(p.getDrug());
                }
                System.out.println(personneList);
                System.out.println(targetList);


                if(selectedAlgoText.equals("Nearest Neighbor")){


                    KNN knn = new KNN(personneList, targetList);
                    drugResult = knn.predict(targetPersonne.toList());

                }else if (selectedAlgoText.equals("Naive Bayes")){

                    NaiveBayes bayes = new NaiveBayes(personneList, targetList);
                    drugResult = bayes.getProbableClass(targetPersonne.toList());

                }else if (selectedAlgoText.equals("Decision Tree")){

                    DecisionTree dt = new DecisionTree();

                    dt.build(personneList, targetList);

                    drugResult = dt.predict(targetPersonne.toList());



                }
                //setting the drug in the database

                targetPersonne.setDrug(drugResult);
                db.updatePersonne(targetPersonne);


                //setting the result in the textview
                linearresult.setVisibility(View.VISIBLE);
                result.setText(drugResult);
                result.setVisibility(View.VISIBLE);

                Toast.makeText(this,
                        "results using " + selectedAlgoText  + " is " + drugResult,
                        Toast.LENGTH_SHORT).show();

            }catch (NullPointerException e){
                Toast.makeText(this, "Please select a choice", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
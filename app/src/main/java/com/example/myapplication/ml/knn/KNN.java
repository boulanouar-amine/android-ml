package com.example.myapplication.ml.knn;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.ml.Utils;
import com.example.myapplication.ml.dt.Predictable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


public class KNN implements Predictable {
    private List<List<String>> table;
    private List<String> classColumn;
    private List<String> uniqueClasses;
    private final double DEFAULT_K_PERCENTAGE = 4.0/10.0;

    public KNN (List<List<String>> table, List<String> classColumn) {
        this.table = table;
        this.classColumn = classColumn;
        this.uniqueClasses = new ArrayList<>(Utils.getUnique(this.classColumn));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public String predict(List<String> feature) {
        int k = (int)(table.size() * DEFAULT_K_PERCENTAGE);

        return this.predict(feature, k);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String predict(List<String> feature, int k) {
        List<Double> distances = new ArrayList<>();
        List<Set<String>> featuresUniques = new ArrayList<>();

        for (int i = 0; i < table.get(0).size(); i++) {
            featuresUniques.add(Utils.getUnique(Utils.getColumns(table, i)));
        }


        for (int i = 0; i < table.size(); i++) {
            List<Double> featuresDistances = new ArrayList<>();

            for (int j = 0; j < table.get(i).size(); j++) {
                int indexTarget = Utils.findFirstIndex(featuresUniques.get(j), feature.get(j));
                int indexRowFeature = Utils.findFirstIndex(featuresUniques.get(j), table.get(i).get(j)) ;

                if (indexRowFeature == indexTarget) featuresDistances.add(0.0);
                else if (featuresUniques.get(j).size() <= 2) featuresDistances.add(1.0);
                else {
                    double dist = Double.valueOf(Math.abs(indexRowFeature - indexTarget)) / Double.valueOf(featuresUniques.get(j).size());
                    featuresDistances.add(dist);
                }
            }

            distances.add(Math.sqrt(featuresDistances.stream().reduce(0.0, (a , b) -> {
                return a + b;
            })));
        }

        List<Double> sortedDistances = new ArrayList<>(distances);

        Collections.sort(sortedDistances);

        double maxNeighbor = sortedDistances.get(k - 1);
        int classOccurrences[] = new int[uniqueClasses.size()];

        for (int i = 0; i < distances.size(); i++) {
            if (distances.get(i) <= maxNeighbor) {
                String classValue = classColumn.get(i);
                int j = uniqueClasses.indexOf(classValue);

                classOccurrences[j]++;
            }
        }

        return uniqueClasses.get(Utils.getIndexOfLargest(classOccurrences));
    }

    
}

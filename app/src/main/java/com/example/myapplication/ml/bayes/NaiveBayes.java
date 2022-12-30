package com.example.myapplication.ml.bayes;

import com.example.myapplication.ml.Utils;

import java.util.List;
import java.util.Set;


public class NaiveBayes {
    private final List<List<String>> table;
    private List<String> classColumn;

    public NaiveBayes (List<List<String>> table, List<String> classColumn) {
        this.table = table;
        this.classColumn = classColumn;
    }

    public String getProbableClass (List<String> features) {
        Set<String> classValues = Utils.getUnique(this.classColumn);
        String probableClass = null;
        double probability = Double.MIN_VALUE;

        for (String value : classValues) {
            double p = this.probability(value, features);

            if (probability < p) {
                probableClass = value;
                probability = p;
            }
        }

        if (probability <= 0) return null;

        return probableClass;
    }

    public double probability (String classValue, int featureIndex, String featureValue) {
        int classOccurence = 0;
        int featureAppearence = 0;

        for (int i = 0; i < this.classColumn.size(); i++) {
            if (!this.classColumn.get(i).equals(classValue)) continue;

            classOccurence++;

            if (this.table.get(i).get(featureIndex).equals(featureValue)) featureAppearence++;
        }

        if (classOccurence <= 0) return 0;

        return Double.valueOf(featureAppearence) / Double.valueOf(classOccurence);
    }

    public double probability (String classValue) {
        int classOccurence = Utils.getOccurrence(this.classColumn, classValue);

        return Double.valueOf(classOccurence) / Double.valueOf(this.classColumn.size());
    }

    public double probability (String classValue, List<String> features) {
        int classOccurence = 0;
        int featuresCount[] = new int[features.size()];
        double p = 1.0;


        for (int i = 0; i < classColumn.size(); i++) {
            if (!this.classColumn.get(i).equals(classValue)) continue;

            classOccurence++;

            for (int j = 0; j < features.size(); j++) {
                if (table.get(i).get(j).equals(features.get(j))) featuresCount[j]++;
            }
        }

        for (int i = 0; i < features.size(); i++) {
            p *= Double.valueOf(featuresCount[i]) / Double.valueOf(classOccurence);
        }

        p *= Double.valueOf(classOccurence) / Double.valueOf(this.classColumn.size());

        return p;
    }
}

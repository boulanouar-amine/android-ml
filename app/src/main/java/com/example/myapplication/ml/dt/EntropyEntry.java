package com.example.myapplication.ml.dt;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class EntropyEntry {
    private final String value;
    private int occurrences;
    private final Map<String, Integer> classValues;
    private final double LOG_2 = Math.log(2);

    public EntropyEntry (String value) {
        this.value = value;
        this.occurrences = 0;
        this.classValues = new HashMap<>();
    }

    public void increaseOccurrences() {
        this.occurrences++;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public String getValue() {
        return value;
    }

    public void increaseClassOccurrence (String classValue) {
        int count = classValues.containsKey(classValue) ? classValues.get(classValue) : 0;
        classValues.put(classValue, count + 1);
    }

    public double calculate () {
        double result = 0;
        
        for (Entry<String, Integer> entry : this.classValues.entrySet()) {
            double x = Double.valueOf(entry.getValue()) / Double.valueOf(this.occurrences);
            result += - x * (Math.log(x) / LOG_2);
        }

        return result;
    }
}

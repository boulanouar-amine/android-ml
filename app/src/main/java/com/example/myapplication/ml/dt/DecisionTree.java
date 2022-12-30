package com.example.myapplication.ml.dt;

import com.example.myapplication.ml.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;



public class DecisionTree implements Predictable {
    private int decisionFeatureIndex = -1;
    private final Map<String, Predictable> leafs;

    public DecisionTree () {
        leafs = new HashMap<>();
    }
    
    public void build (List<List<String>> table, List<String> classValues) {
        int featureCount = table.get(0).size();
        int rowsCount = table.size();
        List<Double> entropies = new ArrayList<>();

        for (int i = 0; i < featureCount; i++) {
            List<String> column = Utils.getColumns(table, i);
            Map<String, EntropyEntry> valuesEntropies = new HashMap<>();
            double entropyValue = 0.0;
            
            for (int j = 0; j < column.size(); j++) {
                String value = column.get(j);
                String classValue = classValues.get(j);
                EntropyEntry entropy = valuesEntropies.get(value);

                if (entropy == null) entropy = new EntropyEntry(value);
                
                entropy.increaseOccurrences();
                entropy.increaseClassOccurrence(classValue);

                valuesEntropies.put(value, entropy);
            }

            for (Entry<String, EntropyEntry> entry: valuesEntropies.entrySet())  {
                entropyValue += (double) entry.getValue().getOccurrences() / rowsCount * entry.getValue().calculate();
            }

            entropies.add(entropyValue); 
        }

        this.decisionFeatureIndex = Utils.getIndexOfSmallest(entropies);

        Set<String> uniqueDecisionValues = Utils.getUnique(Utils.getColumns(table, this.decisionFeatureIndex));

        for (String value : uniqueDecisionValues) {
            List<List<String>> subTable = new ArrayList<>();
            List<String> classSubTable = new ArrayList<>();
            
            for (int i = 0; i < table.size(); i++) {
                if (value.equals(table.get(i).get(this.decisionFeatureIndex))) {
                    subTable.add(table.get(i));
                    classSubTable.add(classValues.get(i));
                }
            }

            subTable = Utils.removeColumns(subTable, this.decisionFeatureIndex);
            
            Set<String> classUniqueValues = Utils.getUnique(classSubTable);

            if (classUniqueValues.size() <= 0) continue;
            
            
            if (classUniqueValues.size() == 1) {
                leafs.put(value, values -> classSubTable.get(0));
            } else {
                DecisionTree subDt = new DecisionTree();
                subDt.build(subTable, classSubTable);
                leafs.put(value, subDt);
            }
        }        
    }

    @Override
    public String predict(List<String> features) {
        String value = features.get(this.decisionFeatureIndex);
        
        if (value == null || !this.leafs.containsKey(value)) return null;

        return leafs.get(value).predict(Utils.removeValue(features, this.decisionFeatureIndex));
    }
}

package com.example.myapplication.ml;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Utils {
    public static <T> List<T> getColumns (List<List<T>> table, int index) {
        List<T> columns = new ArrayList<>();

        for (List<T> row : table) columns.add(row.get(index));

        return columns;
    }

    public static <T> List<List<T>> removeColumns (List<List<T>> table, int index) {
        List<List<T>> values = new ArrayList<>();

        for (List<T> row : table) {
            List<T> newRow = new ArrayList<>();

            for (int i = 0; i < row.size(); i++) {
                if (i != index) newRow.add(row.get(i));
            }

            values.add(newRow);
        }

        return values;
    }

    public static <T> List<T> removeValue (List<T> table, int index) {
        List<T> values = new ArrayList<>();

        for (int i = 0; i < table.size(); i++) {
            if (index != i) values.add(table.get(i));
        }

        return values;
    }

    public static <T> Set<T> getUnique (List<T> table) {
        Set<T> uniqueValues = new TreeSet<>();

        for (T elt : table) uniqueValues.add(elt);

        return uniqueValues;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int getOccurrence (List<? extends Object> lst, Object value) {
        return lst.stream().filter(v -> v.equals(value)).toArray().length;
    }

    public static int getIndexOfSmallest (List<Double> lst) {
        Double min = Double.MAX_VALUE;
        int index = -1;

        for (int i = 0; i < lst.size(); i++) {
            Double elt = lst.get(i);

            if (elt < min) {
                min = elt;
                index = i;
            }
        }

        return index;
    }

    public static int getIndexOfSmallest (double lst[]) {
        Double min = Double.MAX_VALUE;
        int index = -1;

        for (int i = 0; i < lst.length; i++) {
            Double elt = lst[i];

            if (elt < min) {
                min = elt;
                index = i;
            }
        }

        return index;
    }

    public static int getIndexOfLargest (int lst[]) {
        int min = Integer.MIN_VALUE;
        int index = -1;

        for (int i = 0; i < lst.length; i++) {
            int elt = lst[i];

            if (elt > min) {
                min = elt;
                index = i;
            }
        }

        return index;
    }

    public static double median (List<? extends Number> values) {
        double valuesArray[] = new double[values.size()];

        for (int i = 0; i < values.size(); i++) 
            valuesArray[i] = values.get(i).doubleValue();

        return median(valuesArray);
    }

    public static double median (double values[]) {
        double valuesClone[] = values.clone();

        Arrays.sort(valuesClone);

        if (valuesClone.length <= 0) return 0;
        if (valuesClone.length == 1) return valuesClone[0];

        if (valuesClone.length % 2 == 0) {
            return (valuesClone[valuesClone.length/2] + valuesClone[valuesClone.length/2 - 1])/2;
        } else {
            return valuesClone[valuesClone.length / 2];
        }
    }

    public static boolean checkArraysEquals (Number a[], Number b[]) {
        if (a.length != b.length) return false;

        Arrays.sort(a);
        Arrays.sort(b);

        for (int i = 0; i < a.length; i++) {
            if (!a[i].equals(b[i])) return false;
        }

        return true;
    }

    public static <T> List<List<T>> setColumn (List<List<T>> table, List<T> values, int index) {
        List<List<T>> tableClone = cloneTable(table);

        for (int i = 0; i < table.size(); i++) tableClone.get(i).set(index, values.get(i));

        return tableClone;
    }

    public static <T extends Object> int findFirstIndex (Collection<T> collection, T value) {
        Object values[]  = collection.toArray();

        for (int i = 0; i < values.length; i++) {
            if (value.equals(values[i])) return i;
        }

        return -1;
    }

    public static <T> List<List<T>> cloneTable (List<List<T>> table) {
        List<List<T>> tableClone = new ArrayList<>();

        for (int i = 0; i < table.size(); i++) {
            List<T> a = new ArrayList<>();

            for (int j = 0; j < table.get(i).size(); j++) a.add(table.get(i).get(j));

            tableClone.add(a);
        }

        return tableClone;
    }

}

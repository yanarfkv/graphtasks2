package com.company.graph2;

import java.util.HashMap;
import java.util.*;

public class GraphTasks2Solution implements GraphTasks2 {
    @Override
    public HashMap<Integer, Integer> dijkstraSearch(int[][] adjacencyMatrix, int startIndex) {
        boolean[] vertex = new boolean[adjacencyMatrix.length];
        HashMap<Integer,Integer> hashMap  = new HashMap<>();
        vertex[startIndex] = true;
        for (int i = 0; i < adjacencyMatrix.length; i++){
            if (i != startIndex) {
                if (adjacencyMatrix[startIndex][i] == 0) hashMap.put(i, Integer.MAX_VALUE);
                else hashMap.put(i, adjacencyMatrix[startIndex][i]);
            }
        }
        for (int a : hashMap.keySet()){
            for (int i = 0; i < adjacencyMatrix.length; i++){
                if (adjacencyMatrix[a][i] == 0) continue;
                if (i != a && !vertex[i] && adjacencyMatrix[a][i] + hashMap.get(a) < hashMap.get(i)){
                    hashMap.put(i, adjacencyMatrix[a][i] + hashMap.get(a));
                    vertex[i] = true;
                }
            }
        }
        return hashMap;
    }

    @Override
    public Integer primaAlgorithm(int[][] adjacencyMatrix) {
        HashSet<Integer> hashSet = new HashSet<>();
        int count = 0;
        hashSet.add(0);
        int minimum = adjacencyMatrix[0][1];
        int indexOfMinimum = 1;
        for (int i = 1; i < adjacencyMatrix.length; i++)
            if (adjacencyMatrix[0][i] < minimum && adjacencyMatrix[0][i] > 0) {
                minimum = adjacencyMatrix[0][i];
                indexOfMinimum = i;
            }
        hashSet.add(indexOfMinimum);
        count += minimum;
        for (int i = 0; i < adjacencyMatrix.length - 2; i++) {
            int index = 0;
            int minValue = Integer.MAX_VALUE;
            for (Integer a : hashSet) {
                for (int j = 0; j < adjacencyMatrix.length; j++) {
                    if (adjacencyMatrix[a][j] < minValue && adjacencyMatrix[a][j] > 0 && !hashSet.contains(j)) {
                        minValue = adjacencyMatrix[a][j];
                        index = j;
                    }
                }
            }
            hashSet.add(index);
            count += minValue;
        }
        return count;
    }

    @Override
    public Integer kraskalAlgorithm(int[][] adjacencyMatrix) {
        List<Set<Integer>> sets = new ArrayList<>();
        TreeMap<Integer, List<Set<Integer>>> treeMap = new TreeMap<>();
        int isKey;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                Set<Integer> pair = new HashSet<>();
                isKey = adjacencyMatrix[i][j];
                if (isKey > 0) {
                    pair.add(i);
                    pair.add(j);
                    if (!treeMap.containsKey(isKey)) treeMap.put(isKey, new ArrayList<>());
                    boolean check = true;
                    for (Set<Integer> setForCheck : treeMap.get(isKey))
                        if (setForCheck.containsAll(pair)) check = false;
                    if (check) treeMap.get(isKey).add(pair);
                }
            }
        }
        int sum = 0;
        for (Integer a : treeMap.keySet()) {
            int firstIndex = -1;
            int secondIndex = -1;
            for (Set<Integer> valueSet : treeMap.get(a)) {
                if (sets.size() == 0) {
                    sets.add(new HashSet<>(valueSet));
                    sum += a;
                    continue;
                }
                Object[] objects = valueSet.toArray();
                for (int i = 0; i < objects.length; i++) {
                    for (int k = 0; k < sets.size(); k++) {
                        if (sets.get(k).contains((Integer) objects[i])) {
                            if (i == 0) firstIndex = k;
                            if (i == 1) secondIndex = k;
                        }

                    }
                }
                if (firstIndex >= 0 && firstIndex == secondIndex) continue;
                if (firstIndex >= 0 && secondIndex < 0) sets.get(firstIndex).addAll(valueSet);
                if (secondIndex >= 0 && firstIndex < 0) sets.get(secondIndex).addAll(valueSet);
                if (firstIndex >= 0 && secondIndex >= 0) {
                    sets.get(firstIndex).addAll(sets.get(secondIndex));
                    sets.get(secondIndex).clear();
                }
                if (firstIndex < 0 && secondIndex < 0) sets.add(valueSet);
                firstIndex = -1;
                secondIndex = -1;
                sum += a;
            }
        }
        return sum;
    }
}

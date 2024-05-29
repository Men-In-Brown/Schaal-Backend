package com.nighthawk.hacks;

import java.util.ArrayList;
import java.util.List;

import com.nighthawk.spring_portfolio.mvc.grade.Grade;

public class GradeSorter {
    public static List<Grade> sortByProperty(List<Grade> grades, String property) {
        List<Grade> sortedGrades = new ArrayList<>(grades);  // Create a copy of the list to avoid modifying the original list

        for (int i = 1; i < sortedGrades.size(); i++) {
            Grade key = sortedGrades.get(i);
            int j = i - 1;

            while (j >= 0 && compare(sortedGrades.get(j), key, property) > 0) {
                sortedGrades.set(j + 1, sortedGrades.get(j));
                j = j - 1;
            }
            sortedGrades.set(j + 1, key);
        }

        return sortedGrades;
    }

    // Helper method to compare two Grade objects based on the specified property
    private static int compare(Grade g1, Grade g2, String property) {
        switch (property) {
            case "email":
                return g1.getEmail().compareTo(g2.getEmail());
            case "name":
                return g1.getName().compareTo(g2.getName());
            case "assignment":
                return g1.getAssignment().compareTo(g2.getAssignment());
            case "maxPoints":
                return Double.compare(g1.getMaxPoints(), g2.getMaxPoints());
            case "score":
                return Double.compare(g1.getScore(), g2.getScore());
            default:
                throw new IllegalArgumentException("Invalid property: " + property);
        }
    }
}

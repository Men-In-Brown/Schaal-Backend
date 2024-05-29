package com.nighthawk.hacks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.nighthawk.spring_portfolio.mvc.linkr.InternshipDTO;

public class InternshipSearcher {

    public static List<InternshipDTO> searchInternships(List<InternshipDTO> internships, String searchQuery) {
        // Ensure the list is sorted by name
        Collections.sort(internships, Comparator.comparing(InternshipDTO::getName));
        
        List<InternshipDTO> result = new ArrayList<>();
        int index = binarySearch(internships, searchQuery);

        if (index < 0) {
            // No exact match found, return an empty list or continue searching for substring matches
            index = -(index + 1);
        }

        // Check for matches around the found index
        int left = index;
        while (left >= 0 && internships.get(left).getName().toLowerCase().contains(searchQuery.toLowerCase())) {
            result.add(internships.get(left));
            left--;
        }

        int right = index + 1;
        while (right < internships.size() && internships.get(right).getName().toLowerCase().contains(searchQuery.toLowerCase())) {
            result.add(internships.get(right));
            right++;
        }

        return result;
    }

    private static int binarySearch(List<InternshipDTO> internships, String searchQuery) {
        int left = 0;
        int right = internships.size() - 1;
        String lowerCaseQuery = searchQuery.toLowerCase();

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String midName = internships.get(mid).getName().toLowerCase();

            if (midName.contains(lowerCaseQuery)) {
                return mid;
            } else if (midName.compareTo(lowerCaseQuery) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -(left + 1); // Not found
    }
}

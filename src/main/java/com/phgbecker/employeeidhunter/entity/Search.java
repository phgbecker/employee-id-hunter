package com.phgbecker.employeeidhunter.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

@Getter
public class Search {
    private final String searchTerm;
    private final int maxItems;

    public Search(String searchTerm, int maxItems) {
        this.searchTerm = searchTerm;
        this.maxItems = maxItems;
    }

    public String convertToJson() {
        try {
            return new ObjectMapper().valueToTree(this).toString();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Oops, something wrong happened while converting the search to JSON", e);
        }
    }

}

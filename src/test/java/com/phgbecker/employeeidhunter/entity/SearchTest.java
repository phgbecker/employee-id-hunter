package com.phgbecker.employeeidhunter.entity;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchTest {

    @Test
    public void givenSearch_whenConvertingToJson_thenEquals() {
        String expectedJson = "{\"searchTerm\":\"Doe, John\",\"maxItems\":1}";

        assertThat(
                new Search("Doe, John", 1).convertToJson()
        ).isEqualTo(
                expectedJson
        );
    }

}
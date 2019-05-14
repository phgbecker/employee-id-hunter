package com.phgbecker.employeeidhunter.schedule.implementation;

import com.phgbecker.employeeidhunter.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

public class SearchEmployeeId implements Consumer<Employee> {
    private static final Logger log = LoggerFactory.getLogger(SearchEmployeeId.class);
    private final SearchConfiguration searchConfiguration;

    public SearchEmployeeId(SearchConfiguration searchConfiguration) {
        this.searchConfiguration = searchConfiguration;
    }

    /**
     * Given a Consumer<Employee> search the API for a match, updating the ID if so
     */
    @Override
    public void accept(Employee employee) {
        log.info("Searching for employee {} credentials", employee);

        Search search = new Search(employee.getNameOrderedBySurname(), 50);

        try {
            String searchResponse = searchEmployee(search);

            if (!searchResponse.isEmpty()) {
                filterColleagueFromResponse(search, searchResponse)
                        .ifPresent(colleague -> employee.setId(colleague.getAccountName()));
            }

        } catch (Exception e) {
            log.error("Oops, an exception happened while searching for an employee", e);
        }
    }

    private String searchEmployee(Search search) throws IOException {
        String searchJson = search.convertToJson();
        SearchRequest searchRequest = new SearchRequest(searchConfiguration);

        return searchRequest.search(searchJson);
    }

    private Optional<Colleague> filterColleagueFromResponse(Search search, String searchResponse) throws JAXBException {
        Optional<Colleague> colleague = Optional.empty();
        ArrayOfColleague colleagues = ArrayOfColleague.xmlToArrayOfColleague(searchResponse);

        if (!colleagues.getColleagues().isEmpty()) {
            colleague = colleagues.getColleagues().stream()
                    .filter(c -> search.getSearchTerm().equalsIgnoreCase(c.getName()))
                    .findFirst();
        }

        return colleague;
    }

}

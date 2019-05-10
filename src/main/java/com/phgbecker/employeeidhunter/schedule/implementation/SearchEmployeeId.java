package com.phgbecker.employeeidhunter.schedule.implementation;

import com.phgbecker.employeeidhunter.entity.ArrayOfColleague;
import com.phgbecker.employeeidhunter.entity.Colleague;
import com.phgbecker.employeeidhunter.entity.Employee;
import com.phgbecker.employeeidhunter.entity.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SearchEmployeeId implements Consumer<Employee> {
    private static final Logger log = LoggerFactory.getLogger(SearchEmployeeId.class);

    /**
     * Given a Consumer<Employee> search the API for a match, updating the ID if so
     */
    @Override
    public void accept(Employee employee) {
        log.info("Searching for employee {} credentials", employee);

        Search search = new Search(employee.getLastName() + ", " + employee.getFirstName(), 50);

        try {
            String searchResponse = searchEmployee(search);

            if (!searchResponse.isEmpty()) {

                filterColleagueFromResponse(search, searchResponse)
                        .ifPresent(colleague -> employee.setId(colleague.getAccountName()));
            }

        } catch (Exception e) {
            log.error("Oops, an exception happened while searching for an employee", e);
        }

        sleepToAvoidDenialOfService();
    }

    private String searchEmployee(Search search) throws IOException {
        String searchJson = search.convertToJson();

        return SearchRequest.getInstance().search(searchJson);
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

    private void sleepToAvoidDenialOfService() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            log.error("Oops, an exception happened while sleeping the thread", e);

            Thread.currentThread().interrupt();
        }
    }

}

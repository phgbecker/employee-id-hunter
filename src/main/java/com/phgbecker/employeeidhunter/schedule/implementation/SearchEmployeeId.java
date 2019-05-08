package com.phgbecker.employeeidhunter.schedule.implementation;

import com.phgbecker.employeeidhunter.entity.ArrayOfColleague;
import com.phgbecker.employeeidhunter.entity.Colleague;
import com.phgbecker.employeeidhunter.entity.Employee;
import com.phgbecker.employeeidhunter.entity.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SearchEmployeeId implements Consumer<Employee> {
    private static final Logger log = LoggerFactory.getLogger(SearchEmployeeId.class);
    private static final String API_URL = "";
    private static final String API_AUTH_TOKEN = "";

    /**
     * Given a Consumer<Employee> search the API for a match, updating the ID if so
     */
    @Override
    public void accept(Employee employee) {
        log.info("Searching for employee {} credentials", employee);

        Search search = new Search(employee.getLastName() + ", " + employee.getFirstName(), 1);

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
        HttpURLConnection connection = getConnection();

        postEmployeeSearch(searchJson, connection);

        return getEmployeeSearchResponse(connection);
    }

    private HttpURLConnection getConnection() throws IOException {
        HttpURLConnection connection;

        try {
            connection = (HttpURLConnection) new URL(API_URL).openConnection();
            connection.setInstanceFollowRedirects(true);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
            connection.setRequestProperty("Authorization", API_AUTH_TOKEN);
        } catch (IOException e) {
            throw new IOException("Oops, something wrong happened while setting up the connection", e);
        }

        return connection;
    }

    private void postEmployeeSearch(String search, HttpURLConnection connection) throws IOException {
        try (DataOutputStream dataOutStream = new DataOutputStream(connection.getOutputStream())) {
            dataOutStream.writeBytes(search);
            dataOutStream.flush();
        } catch (IOException e) {
            throw new IOException("Oops, something wrong happened while posting the search", e);
        }
    }

    private String getEmployeeSearchResponse(HttpURLConnection connection) throws IOException {
        InputStream stream = connection.getInputStream();
        StringBuilder response = new StringBuilder();

        try (BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(stream))) {
            String bufferString;

            while ((bufferString = inputBuffer.readLine()) != null) {
                response.append(bufferString);
            }
        } catch (IOException e) {
            throw new IOException("Oops, something wrong happened while reading the search response", e);
        }

        return response.toString();
    }

    private Optional<Colleague> filterColleagueFromResponse(Search search, String searchResponse) throws XMLStreamException, JAXBException {
        Optional<Colleague> colleague = Optional.empty();
        ArrayOfColleague colleagues = ArrayOfColleague.xmlToArrayOfColleague(searchResponse);

        if (colleagues.getColleagues() != null && !colleagues.getColleagues().isEmpty()) {
            colleague = colleagues.getColleagues().stream()
                    .filter(c -> search.getSearchItem().equals(c.getName()))
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

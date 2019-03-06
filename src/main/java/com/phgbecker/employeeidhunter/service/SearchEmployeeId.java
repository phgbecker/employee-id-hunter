package com.phgbecker.employeeidhunter.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phgbecker.employeeidhunter.entity.ArrayOfColleague;
import com.phgbecker.employeeidhunter.entity.Employee;
import com.phgbecker.employeeidhunter.entity.Search;

public class SearchEmployeeId implements Consumer<Employee> {
	private static Logger log = LoggerFactory.getLogger(SearchEmployeeId.class);
	private static final String API_URL = "";
	private static final String API_AUTH_TOKEN = "";
	private static final String NAMESPACE = "";
	/**
	 * Given a Consumer<Employee>, search the API for a match updating the ID if so
	 */
	@Override
	public void accept(Employee employee) {
		log.info("Searching for employee {} credential", employee);

		Search search = new Search(employee.getLastName() + ", " + employee.getFirstName(), 1);
		JsonNode jsonNode = new ObjectMapper().valueToTree(search);

		try {
			HttpURLConnection connectionInstance = getHttpURLConnection();

			postEmployeeSearch(jsonNode.toString(), connectionInstance);

			String searchResponse = getEmployeeSearchResponse(connectionInstance);

			if (!searchResponse.isEmpty()) {
				// Removes the default namespace to avoid parsing marshalling errors
				String xml = searchResponse.replace(NAMESPACE, "");

				ArrayOfColleague colleagues = xmlToArrayOfColleague(xml);

				if (colleagues.getColleagues() != null && !colleagues.getColleagues().isEmpty()) {
					// If a match was found, update the Employee ID attribute
					String id = colleagues.getColleagues().get(0).getAccountName();
					employee.setId(id);
				}
			}

		} catch (Exception e) {
			log.error("Oops, an exception happened while searching for an employee", e);
		}

		// Sleeps 2s to avoid DoS
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			log.error("Oops, and exception happend while sleeping the thread", e);

			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Sets up and returns the connection instance
	 *
	 * @return Connection instance
	 * @throws IOException Exception
	 */
	private HttpURLConnection getHttpURLConnection() throws IOException {
		HttpURLConnection connection;

		try {
			connection = (HttpURLConnection) new URL(API_URL).openConnection();
			connection.setInstanceFollowRedirects(true);
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(10000);
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
			connection.setRequestProperty("Authorization", API_AUTH_TOKEN);
		} catch (IOException e) {
			throw new IOException("Oops, something wrong happened while setting up the connection");
		}

		return connection;
	}

	/**
	 * POST the employee search
	 *
	 * @param employeeJson Employee JSON file
	 * @param connection Connection instance
	 * @throws Exception Exception
	 */
	private void postEmployeeSearch(String employeeJson, HttpURLConnection connection) throws Exception {
		try (DataOutputStream dataOutStream = new DataOutputStream(connection.getOutputStream())) {
			dataOutStream.writeBytes(employeeJson);
			dataOutStream.flush();
		} catch (Exception e) {
			throw new Exception("Oops, something wrong happened while posting the search - " + e.getMessage());
		}
	}

	/**
	 * Reads the search response
	 *
	 * @param connection Connection instance
	 * @return String
	 * @throws Exception Exception
	 */
	private String getEmployeeSearchResponse(HttpURLConnection connection) throws Exception {
		InputStream stream = connection.getInputStream();
		StringBuilder response = new StringBuilder();

		try (BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(stream))) {
			String bufferString;

			while((bufferString = inputBuffer.readLine()) != null) {
				response.append(bufferString);
			}
		} catch (Exception e) {
			throw new Exception("Oops, something wrong happened while reading the search response - " + e.getMessage());
		}

		return response.toString();
	}

	/**
	 * Given an XML, unmarshalls it to an ArrayOfColleague
	 *
	 * @param xml XML
	 * @return ArrayOfColleague
	 * @throws JAXBException Exception
	 */
	private ArrayOfColleague xmlToArrayOfColleague(String xml) throws JAXBException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ArrayOfColleague.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			return (ArrayOfColleague) unmarshaller.unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			throw new JAXBException("Oops, something wrong happened while unmarshalling the XML - " + e.getMessage());
		}
	}

}

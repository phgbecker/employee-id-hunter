package com.phgbecker.employeeidhunter.entity;

import org.junit.Test;

import javax.xml.bind.JAXBException;

import static org.assertj.core.api.Assertions.assertThat;

public class ArrayOfColleagueTest {

    @Test
    public void givenAnXml_whenParsingToObject_thenIsAnInstanceOfArrayOfColleague() throws JAXBException {
        String givenXml = "<ArrayOfColleague xmlns=\"uri://newsgator.com/social\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"/>";

        assertThat(
                ArrayOfColleague.xmlToArrayOfColleague(givenXml)
        ).isInstanceOf(
                ArrayOfColleague.class
        );
    }

    @Test
    public void givenAnXmlWithAColleague_whenParsingToObject_thenColleagueAccountNameEquals() throws JAXBException {
        String givenXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<ArrayOfColleague xmlns=\"uri://newsgator.com/social\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                "   <Colleague>" +
                "      <AccountName>eID</AccountName>" +
                "   </Colleague>" +
                "</ArrayOfColleague>";

        assertThat(
                ArrayOfColleague.xmlToArrayOfColleague(givenXml).getColleagues().get(0).getAccountName()
        ).isEqualTo(
                new Colleague("eID", "Doe, Joe").getAccountName()
        );
    }

    @Test
    public void givenAnXmlWithAColleague_whenParsingToObject_thenColleagueNameEquals() throws JAXBException {
        String givenXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<ArrayOfColleague xmlns=\"uri://newsgator.com/social\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                "   <Colleague>" +
                "      <Name>Doe, Joe</Name>" +
                "   </Colleague>" +
                "</ArrayOfColleague>";

        assertThat(
                ArrayOfColleague.xmlToArrayOfColleague(givenXml).getColleagues().get(0).getName()
        ).isEqualTo(
                new Colleague("eID", "Doe, Joe").getName()
        );
    }

}
package com.phgbecker.employeeidhunter.entity;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "ArrayOfColleague")
@XmlAccessorType(XmlAccessType.FIELD)
public class ArrayOfColleague {

    @XmlElement(name = "Colleague")
    private List<Colleague> colleagues;

    public List<Colleague> getColleagues() {
        return colleagues == null ? Collections.emptyList() : Collections.unmodifiableList(colleagues);
    }

    /**
     * Given an XML, unmarshalls it to an ArrayOfColleague
     *
     * @param xml XML
     * @return ArrayOfColleague
     * @throws JAXBException Exception
     */
    public static ArrayOfColleague xmlToArrayOfColleague(String xml) throws JAXBException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ArrayOfColleague.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return (ArrayOfColleague) unmarshaller.unmarshal(
                    new StringReader(removeXmlNamespace(xml))
            );
        } catch (JAXBException e) {
            throw new JAXBException("Oops, something wrong happened while unmarshalling the XML", e);
        }
    }

    private static String removeXmlNamespace(String xml) {
        Objects.requireNonNull(xml, "The XML has not been supplied");

        return xml.replaceFirst("xmlns[^\"]+\"[^\"]+\"", "");
    }

}

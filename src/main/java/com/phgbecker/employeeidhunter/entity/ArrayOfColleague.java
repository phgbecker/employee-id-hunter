package com.phgbecker.employeeidhunter.entity;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.util.Collections;
import java.util.List;

@XmlRootElement(name = "ArrayOfColleague")
@XmlAccessorType(XmlAccessType.FIELD)
public class ArrayOfColleague {

    @XmlElement(name = "Colleague")
    private List<Colleague> colleagues;

    public List<Colleague> getColleagues() {
        return Collections.unmodifiableList(colleagues);
    }

    /**
     * Given an XML, unmarshalls it to an ArrayOfColleague
     *
     * @param xml XML
     * @return ArrayOfColleague
     * @throws JAXBException Exception
     */
    public static ArrayOfColleague xmlToArrayOfColleague(String xml) throws XMLStreamException, JAXBException {

        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();

            // To avoid unmarshalling errors, change default behaviour
            xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);

            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new StreamSource(xml));

            JAXBContext jaxbContext = JAXBContext.newInstance(ArrayOfColleague.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return (ArrayOfColleague) unmarshaller.unmarshal(xmlStreamReader);
        } catch (XMLStreamException e) {
            throw new XMLStreamException("Oops, something wrong happened while reading the XML", e);
        } catch (JAXBException e) {
            throw new JAXBException("Oops, something wrong happened while unmarshalling the XML", e);
        }
    }

}

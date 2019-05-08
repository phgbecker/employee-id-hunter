package com.phgbecker.employeeidhunter.entity;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@XmlRootElement(name = "Colleague")
@XmlAccessorType(XmlAccessType.FIELD)
public class Colleague {

    @XmlElement(name = "AccountName")
    private String accountName;

    @XmlElement(name = "Name")
    private String name;

    @XmlElement(name = "TextAlias")
    private String textAlias;

}

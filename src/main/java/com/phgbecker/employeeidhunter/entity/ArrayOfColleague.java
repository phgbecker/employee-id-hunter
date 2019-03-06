package com.phgbecker.employeeidhunter.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "ArrayOfColleague")
@XmlAccessorType(XmlAccessType.FIELD)
public class ArrayOfColleague {

	@XmlElement(name = "Colleague")
	private List<Colleague> colleagues;

}

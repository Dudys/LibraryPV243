package cz.muni.fi.pv243.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement
public class Book {

	private long id;
	private String authorName;
	private String authorSurname;
	private int year;
}

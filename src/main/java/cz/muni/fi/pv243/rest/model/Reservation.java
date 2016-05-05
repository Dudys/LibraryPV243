package cz.muni.fi.pv243.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement
public class Reservation {

	private long id;
	private Date from;
	private Date to;
	private long userId;
	private long bookId;

}

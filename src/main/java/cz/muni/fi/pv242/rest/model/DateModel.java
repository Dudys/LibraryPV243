package cz.muni.fi.pv242.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement()
public class DateModel {
	
	private java.util.Calendar date;

}

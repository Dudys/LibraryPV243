package cz.muni.fi.pv243.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

import cz.muni.fi.pv243.api.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement
public class User {

	private long id;
	private String name;
	private String surname;
	private String email;
	private List<UserRole> roles;
}



package cz.muni.fi.pv242.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

import cz.muni.fi.pv242.persistence.entity.UserRole;
import lombok.Data;

/**
 * Created by honza on 5/18/16.
 */

@Data
@XmlRootElement()
public class User {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private List<UserRole> roles;
    private int age;
    private boolean enabled;
    private String password;
}

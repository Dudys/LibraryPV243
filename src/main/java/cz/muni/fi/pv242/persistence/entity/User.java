package cz.muni.fi.pv242.persistence.entity;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
//User is a reserved SQL table, so we need to change it to avoid conflicts
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    private String passwordHash;

    @NotNull
    private Boolean enabled;

    @NotNull
    private Integer age;

    @Column(nullable = false, unique = true)
    @NotNull
    @Pattern(regexp = ".+@.+\\....?")
    private String email;

    @ElementCollection(targetClass = UserRole.class)
    @JoinTable(name = "userRoles", joinColumns = @JoinColumn(name = "role"))
    @Column(name = "roles", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<UserRole> roles;

    @OneToMany
    private List<Borrowing> borrowings;

    @OneToMany
    private List<Reservation> reservations;

    public void setId()
    {
        id = null;
    }

    public void enableUser(){
        enabled = true;
    }

    public void disableUser(){
        enabled = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (age != null ? !age.equals(user.age) : user.age != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}

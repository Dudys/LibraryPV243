package cz.muni.fi.pv242.persistence.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
//User is a reserved SQL table, so we need to change it to avoid conflicts
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String fullName;

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
    @JoinTable(name = "userRoles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "roles", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<UserRole> roles;

    @OneToMany
    private List<Borrowing> borrowings;

    @OneToMany
    private List<Reservation> reservations;


    public void enableUser(){
        enabled = true;
    }

    public void disableUser(){
        enabled = false;
    }



}

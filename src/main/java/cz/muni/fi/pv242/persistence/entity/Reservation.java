package cz.muni.fi.pv242.persistence.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @OneToOne
    private Book reservedBook;

    @Temporal(TemporalType.TIMESTAMP)
    @Future
    private java.util.Calendar reservationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar borrowingDate;


}

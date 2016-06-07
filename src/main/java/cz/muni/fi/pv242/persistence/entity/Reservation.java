package cz.muni.fi.pv242.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    private Book reservedBook;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar reservationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar borrowingDate;


}

package cz.muni.fi.pv242.persistence.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Calendar;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class Borrowing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Calendar endDate;

}

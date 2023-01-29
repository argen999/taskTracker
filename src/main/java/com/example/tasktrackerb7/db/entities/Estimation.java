package com.example.tasktrackerb7.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "estimations")
@Getter
@Setter
@NoArgsConstructor
public class Estimation {

    @Id
    @SequenceGenerator(name = "estimation_gen", sequenceName = "estimation_seq", allocationSize = 1, initialValue = 6)
    @GeneratedValue(generator = "estimation_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfStart;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfFinish;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH})
    private Card card;

}

package com.example.tasktrackerb7.db.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static javax.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Label {

    @Id
    @SequenceGenerator(name = "label_gen", sequenceName = "label_seq", allocationSize = 1, initialValue = 6)
    @GeneratedValue(generator = "label_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String description;

    private String color;

}

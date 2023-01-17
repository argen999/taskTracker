package com.example.tasktrackerb7.db.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Label {

    @Id
    @SequenceGenerator(name = "label_gen", sequenceName = "label_seq", allocationSize = 1)
    @GeneratedValue(generator = "label_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String text;

    private String color;

}

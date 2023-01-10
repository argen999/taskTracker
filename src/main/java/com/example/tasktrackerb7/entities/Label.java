package com.example.tasktrackerb7.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "label_generator")
    @SequenceGenerator(name = "label_sequence", sequenceName = "label_sequence", allocationSize = 1)
    private Long id;
    private String text;
    @Enumerated(EnumType.STRING)
    private Color color;

}

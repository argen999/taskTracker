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
    @SequenceGenerator(name = "label_seq",sequenceName = "label_seq",allocationSize = 1)
    @GeneratedValue(generator = "label_seq",strategy = GenerationType.SEQUENCE)
    private Long id;

    private String text;

    private Color color;

}

package com.example.tasktrackerb7.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attachment_generator")
    @SequenceGenerator(name = "attachment_sequence", sequenceName = "attachment_sequence", allocationSize = 1)
    private Long id;
    private String attachment;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfStart;
}

package com.example.tasktrackerb7.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Attachment {

    @Id
    @SequenceGenerator(name = "attachment_gen", sequenceName = "attachment_seq", allocationSize = 1)
    @GeneratedValue(generator = "attachment_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String attachment;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfStart;
}

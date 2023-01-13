package com.example.tasktrackerb7.entities;

import javax.persistence.*;
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
    @SequenceGenerator(name = "attachment_seq", sequenceName = "attachment_seq", allocationSize = 1)
    @GeneratedValue(generator = "attachment_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String attachment;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfStart;
}

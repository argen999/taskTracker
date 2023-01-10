package com.example.tasktrackerb7.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;

@Entity
@Table(name = "columns")
@Getter
@Setter
@NoArgsConstructor
public class Column {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "column_generator")
    @SequenceGenerator(name = "column_sequence", sequenceName = "column_sequence", allocationSize = 1)
    private Long id;
    private String name;
    @ManyToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = EAGER)
    private Board board;
    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = EAGER, mappedBy = "column")
    private List<Card> cards;


}

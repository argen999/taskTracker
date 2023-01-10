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
    @SequenceGenerator(name = "column_seq",sequenceName = "column_seq",allocationSize = 1)
    @GeneratedValue(generator = "column_seq",strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = EAGER)
    private Board board;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = EAGER, mappedBy = "column")
    private List<Card> cards;


}

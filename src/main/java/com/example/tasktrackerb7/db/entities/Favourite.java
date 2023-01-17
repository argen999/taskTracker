package com.example.tasktrackerb7.db.entities;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "favourites")
@Getter
@Setter
@NoArgsConstructor
public class Favourite {

    @Id
    @SequenceGenerator(name = "favourite_gen", sequenceName = "favourite_seq", allocationSize = 1)
    @GeneratedValue(generator = "favourite_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH})
    private Workspace workspace;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH})
    private Board board;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private User user;

}

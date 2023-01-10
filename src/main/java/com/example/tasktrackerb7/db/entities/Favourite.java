package com.example.tasktrackerb7.db.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;

@Entity
@Table(name = "favourites")
@Getter
@Setter
@NoArgsConstructor
public class Favourite {

    @Id
    @SequenceGenerator(name = "favourite_seq",sequenceName = "favourite_seq",allocationSize = 1)
    @GeneratedValue(generator = "favourite_seq",strategy = GenerationType.SEQUENCE)
    private Long id;

    private WorkSpace workSpace;

    private Board board;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH})
    private User userId;

}

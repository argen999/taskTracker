package com.example.tasktrackerb7.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;
@Entity
@Table(name = "items")
@Getter@Setter
@NoArgsConstructor
public class Item {

    @Id
    @SequenceGenerator(name = "item_seq",sequenceName = "item_seq",allocationSize = 1)
    @GeneratedValue(generator = "item_seq",strategy = GenerationType.SEQUENCE)
    private Long id;

    private String text;

    private boolean isDone = false;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = EAGER)
    private Checklist checklist;
}

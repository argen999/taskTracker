package com.example.tasktrackerb7.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @SequenceGenerator(name = "comment_seq",sequenceName = "comment_seq",allocationSize = 1)
    @GeneratedValue(generator = "comment_seq",strategy = GenerationType.SEQUENCE)
    private Long id;

    private String text;

    private LocalDate dateOfStart;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = LAZY)
    private List<User> users;


  @ManyToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = LAZY)
  private Card cards;
}

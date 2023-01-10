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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_gen")
    @SequenceGenerator(name = "comment_seq", sequenceName = "comment_seq", allocationSize = 1)
    private Long commentId;
    private String text;
    private LocalDate dateOfStart;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = LAZY, mappedBy = "comment")
    private List<User> users;


  @ManyToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = LAZY)
  private Card card;
}

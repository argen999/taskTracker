package com.example.tasktrackerb7.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.*;

import static jakarta.persistence.FetchType.LAZY;


@Entity
@Table(name = "user_workSpace_roles")
@Getter
@Setter
@NoArgsConstructor
public class User_WorkSpace_Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_workSpace_role_generator")
    @SequenceGenerator(name = "user_workSpace_role_sequence", sequenceName = "user_workSpace_role_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = LAZY)
    private User userId;

   @ManyToOne(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = LAZY)
   private WorkSpace workSpace_id;

   @ManyToOne(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = LAZY)
   private Role role_id;
}

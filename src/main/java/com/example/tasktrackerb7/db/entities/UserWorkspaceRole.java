package com.example.tasktrackerb7.db.entities;

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
public class UserWorkspaceRole {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_workSpace_role_seq")
    @SequenceGenerator(name = "user_workSpace_role_seq", sequenceName = "user_workSpace_role_seq", allocationSize = 1)
    private Long id;


    @ManyToOne(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = LAZY)
    private User userId;

   @ManyToOne(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = LAZY)
   private WorkSpace workSpace_id;

   @ManyToOne(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = LAZY)
   private Role roleId;
}

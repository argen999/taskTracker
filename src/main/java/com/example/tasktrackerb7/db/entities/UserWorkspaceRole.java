package com.example.tasktrackerb7.db.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "userWorkSpaceRoles")
@Getter
@Setter
@NoArgsConstructor
public class UserWorkspaceRole {

    @Id
    @SequenceGenerator(name = "userWorkSpaceRole_seq",sequenceName = "userWorkSpaceRole_seq",allocationSize = 1)
    @GeneratedValue(generator = "userWorkSpaceRole_seq",strategy = GenerationType.SEQUENCE)
    private Long id;


    @ManyToOne(cascade = {DETACH, MERGE, REFRESH, REMOVE})
    private User userId;

   @ManyToOne(cascade = {DETACH, MERGE, REFRESH, REMOVE})
   private WorkSpace workSpace_id;

   @ManyToOne(cascade = {DETACH, MERGE, REFRESH, REMOVE})
   private Role roleId;
}

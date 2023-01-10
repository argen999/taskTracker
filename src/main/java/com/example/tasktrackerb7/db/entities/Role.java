package com.example.tasktrackerb7.db.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @SequenceGenerator(name = "role_seq",sequenceName = "role_seq",allocationSize = 1)
    @GeneratedValue(generator = "role_seq",strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @ManyToMany(targetEntity = User.class, mappedBy = "roles",
            cascade = {DETACH, MERGE, REFRESH})
    private List<User> users;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE},mappedBy = "roleId")
    private List<UserWorkspaceRole>user_workSpace_roles;
}

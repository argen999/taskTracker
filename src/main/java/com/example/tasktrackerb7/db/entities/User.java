package com.example.tasktrackerb7.db.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String surname;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH, REMOVE})
    private AuthInfo authInfo;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE})
    private List<Notification> notification;


    @ManyToMany(targetEntity = Role.class, cascade = ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;


    @ManyToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE})
    private List<Card> cards;


    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, mappedBy = "userId")
    private List<UserWorkspaceRole> user_workSpace_roles;


    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE})
    private List<Favourite> favourites;
}

package com.example.tasktrackerb7.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long userId;

    private String name;

    private String surname;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH, REMOVE})
    private AuthInfo authInfo;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = LAZY)
    private List<Notification> notification;



    @ManyToMany(targetEntity = Role.class, cascade = ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;


    @ManyToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE},fetch = LAZY)
    private List<Card> cards;


    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE},fetch = LAZY,mappedBy = "userId")
    private List<User_Workspace_Role> user_workSpace_roles;



    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = LAZY)
    private List<Favourite> favourites;
}

package com.example.tasktrackerb7.db.entities;

import javax.persistence.*;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class Notification {

    @Id
    @SequenceGenerator(name = "notification_gen", sequenceName = "notification_seq", allocationSize = 1, initialValue = 6)
    @GeneratedValue(generator = "notification_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String text;

    private boolean status;

    private LocalDate dateOfWrite;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH, PERSIST})
    private User fromUser;

    @ManyToMany(cascade = {DETACH, MERGE, REFRESH}, targetEntity = User.class)
    @JoinTable(name = "users_notifications",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH, PERSIST})
    private Board board;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH, PERSIST})
    private Column column;

    @OneToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = FetchType.LAZY)
    private Card card;

    public void addUser(User user) {
        if (users == null) users = new ArrayList<>();
        users.add(user);
    }

    public void remove(User user){
        this.users.remove(user);
        user.getNotifications().remove(this);
    }

}

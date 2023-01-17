package com.example.tasktrackerb7.db.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1, initialValue = 6)
    @GeneratedValue(generator = "user_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String password;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE})
    private List<Notification> notification;

    @OneToMany(cascade = {DETACH, REFRESH, MERGE, PERSIST}, mappedBy = "creator")
    private List<Workspace> workspaces;

    @JsonIgnore
    @ManyToMany(targetEntity = Role.class, cascade = ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @ManyToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE})
    private List<Card> cards;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, mappedBy = "user")
    private List<UserWorkspaceRole> userWorkspaceRoles;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE})
    private List<Favourite> favourites;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for (Role role : roles) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(role.getName()));
        }
        return grantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addRole(Role role) {
        if (roles == null) roles = new ArrayList<>();
        roles.add(role);
    }
}

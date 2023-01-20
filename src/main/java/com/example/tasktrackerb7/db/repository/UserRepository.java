package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.User;
import com.example.tasktrackerb7.db.entities.UserWorkspaceRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email=:email")
    Optional<User> findByEmail(String email);

    @Query("select case when count(u)>0 then true else false end from User u where u.email=:email")
    boolean existsByEmail(@Param(value = "email") String email);

//    @Query("select u from User where UserWorkspaceRole.user.id=:id")
//    Optional<User> findUserById(@Param(value = "id") Long id);

}
package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r where r.id=:id")
    Optional<Role> findById(Long id);

//    @Query("select r from roles as r where (select * from users where )" ,nativeQuery = true)
//    Role findById(String userEmail);

}
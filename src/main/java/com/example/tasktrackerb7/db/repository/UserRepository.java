package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.User;
import com.example.tasktrackerb7.dto.response.MemberResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email=:email")
    Optional<User> findByEmail(String email);

    @Query("select case when count(u)>0 then true else false end from User u where u.email=:email")
    boolean existsByEmail(@Param(value = "email") String email);

    @Query("select new com.example.tasktrackerb7.dto.response.MemberResponse(" +
            "u.id, u.name, u.surname, u.email, u.photoLink) " +
            "from User u join UserWorkspaceRole w " +
            "on w.workspace.id=:id and w.user.id=u.id " +
            "where concat(u.name, u.surname, u.email) like concat('%', :email, '%') ")
    List<MemberResponse> searchByEmailOrName(@Param("email") String email, @Param("id") Long id);

    @Query(value = "select * from users where id in (select users_id from cards_users where cards_id = :id)", nativeQuery = true)
    List<User> getAll(Long id);


}
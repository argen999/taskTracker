package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select  c from Comment  c  where c.card.id = ?1")
    List<Comment> getAllComments(Long id);


}
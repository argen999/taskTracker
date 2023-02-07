package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
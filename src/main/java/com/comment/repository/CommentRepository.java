package com.comment.repository;

import com.comment.model.CommentVOForComRep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentVOForComRep, Integer> {
}

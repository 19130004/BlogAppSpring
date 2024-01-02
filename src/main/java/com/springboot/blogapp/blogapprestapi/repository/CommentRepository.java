package com.springboot.blogapp.blogapprestapi.repository;

import com.springboot.blogapp.blogapprestapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);

}

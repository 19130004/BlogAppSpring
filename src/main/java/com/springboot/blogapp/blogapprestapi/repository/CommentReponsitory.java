package com.springboot.blogapp.blogapprestapi.repository;

import com.springboot.blogapp.blogapprestapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CommentReponsitory extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);

}

package com.springboot.blogapp.blogapprestapi.repository;

import com.springboot.blogapp.blogapprestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategoryId(Long categoryId);
}

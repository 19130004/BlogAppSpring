package com.springboot.blogapp.blogapprestapi.repository;

import com.springboot.blogapp.blogapprestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReponsitory extends JpaRepository<Post, Long> {
}

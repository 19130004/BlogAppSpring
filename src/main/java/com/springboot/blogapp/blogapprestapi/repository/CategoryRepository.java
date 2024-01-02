package com.springboot.blogapp.blogapprestapi.repository;

import com.springboot.blogapp.blogapprestapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

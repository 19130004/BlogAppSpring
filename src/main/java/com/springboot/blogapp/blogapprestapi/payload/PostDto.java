package com.springboot.blogapp.blogapprestapi.payload;

import com.springboot.blogapp.blogapprestapi.entity.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Data
public class PostDto {
    private Long id;
    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    private String title;
    @NotEmpty(message = "Description cannot be empty")
    @Size(min = 10, max = 100, message = "Description must be between 10 and 100 characters")
    private String description;
    @NotEmpty(message = "Content cannot be empty")
    @Size(min = 10, message = "Content must be at least 10 characters")
    private String content;
    private Set<CommentDto> comments;

}

package com.springboot.blogapp.blogapprestapi.payload;

import com.springboot.blogapp.blogapprestapi.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Data
@Schema(name = "PostDto", description = "This is a DTO class for post")
public class PostDto {

    private Long id;
    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    @Schema(description = "Title of the post", example = "This is a title")
    private String title;
    @NotEmpty(message = "Description cannot be empty")
    @Size(min = 10, max = 100, message = "Description must be between 10 and 100 characters")
    @Schema(description = "Description of the post", example = "This is a description")
    private String description;
    @NotEmpty(message = "Content cannot be empty")
    @Size(min = 10, message = "Content must be at least 10 characters")
    @Schema(description = "Content of the post", example = "This is a content")
    private String content;
    private Set<CommentDto> comments;
    private Long categoryId;

}

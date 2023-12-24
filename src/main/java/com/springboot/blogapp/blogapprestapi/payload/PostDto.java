package com.springboot.blogapp.blogapprestapi.payload;

import com.springboot.blogapp.blogapprestapi.entity.Comment;
import lombok.*;

import java.util.Set;

@Data
public class PostDto {
    private String title;
    private String description;
    private String content;
    private Set<CommentDto> comments;

}

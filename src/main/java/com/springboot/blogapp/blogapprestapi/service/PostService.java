package com.springboot.blogapp.blogapprestapi.service;


import com.springboot.blogapp.blogapprestapi.payload.PostDto;
import com.springboot.blogapp.blogapprestapi.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSiz, String sortBy, String sortOrder);
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    void deletePostById(Long id);
    List<PostDto> getPostsByCategoryId(Long categoryId);
}

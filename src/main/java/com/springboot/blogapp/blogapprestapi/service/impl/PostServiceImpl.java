package com.springboot.blogapp.blogapprestapi.service.impl;

import com.springboot.blogapp.blogapprestapi.entity.Post;
import com.springboot.blogapp.blogapprestapi.exception.ResourceNotFoundException;
import com.springboot.blogapp.blogapprestapi.payload.PostDto;
import com.springboot.blogapp.blogapprestapi.payload.PostResponse;
import com.springboot.blogapp.blogapprestapi.repository.PostReponsitory;
import com.springboot.blogapp.blogapprestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostReponsitory postReponsitory;
    private ModelMapper mapper ;

    public PostServiceImpl(PostReponsitory postReponsitory, ModelMapper mapper) {
        this.postReponsitory = postReponsitory;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post newPost = postReponsitory.save(post);
        PostDto postResponse = mapToDTO(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortOrder) {
        Sort sort =  sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postReponsitory.findAll(pageable);

        List<Post> postList = posts.getContent();
        List<PostDto> content = postList.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements((int) posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postReponsitory.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Optional<Post> post = postReponsitory.findById(id);
        if (post.isPresent()) {
            Post postUpdate = post.get();
            postUpdate.setTitle(postDto.getTitle());
            postUpdate.setDescription(postDto.getDescription());
            postUpdate.setContent(postDto.getContent());
            Post postUpdated = postReponsitory.save(postUpdate);
            return mapToDTO(postUpdated);
        } else {
            throw new ResourceNotFoundException("Post", "Id", id);
        }
    }

    @Override
    public void deletePostById(Long id) {
        Optional<Post> post = postReponsitory.findById(id);
        if (post.isPresent()) {
            postReponsitory.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Post", "Id", id);
        }
    }

    private PostDto mapToDTO(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class);
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);
        return post;
    }
}

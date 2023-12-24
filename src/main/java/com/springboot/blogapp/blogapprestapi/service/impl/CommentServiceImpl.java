package com.springboot.blogapp.blogapprestapi.service.impl;

import com.springboot.blogapp.blogapprestapi.entity.Comment;
import com.springboot.blogapp.blogapprestapi.entity.Post;
import com.springboot.blogapp.blogapprestapi.exception.BlogAPIException;
import com.springboot.blogapp.blogapprestapi.payload.CommentDto;
import com.springboot.blogapp.blogapprestapi.repository.CommentReponsitory;
import com.springboot.blogapp.blogapprestapi.repository.PostReponsitory;
import com.springboot.blogapp.blogapprestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentReponsitory commentReponsitory;
    private PostReponsitory postReponsitory;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentReponsitory commentReponsitory, PostReponsitory postReponsitory,  ModelMapper mapper) {
        this.commentReponsitory = commentReponsitory;
        this.postReponsitory = postReponsitory;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Optional<Post> post = postReponsitory.findById(postId);
        if (post.isPresent()) {
            comment.setPost(post.get());
        }
        Comment newComment = commentReponsitory.save(comment);
        CommentDto newCommentDto = mapToDto(newComment);
        return newCommentDto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentReponsitory.findByPostId(postId);
        List<CommentDto> commentDtos = comments.stream().map(this::mapToDto).collect(Collectors.toList());
        return commentDtos;

    }

    @Override
    public CommentDto getCommentById(long id, long commentId) {
        Post post = postReponsitory.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        Comment comment = commentReponsitory.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found with id " + commentId));
        if (comment.getPost().getId() == post.getId()) {
            return mapToDto(comment);
        } else {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment not found");
        }

    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Post post = postReponsitory.findById(postId).orElseThrow(() -> new RuntimeException("Post not found with id " + postId));
        Comment comment = commentReponsitory.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found with id " + commentId));
        if (comment.getPost().getId() == post.getId()) {
            comment.setName(commentDto.getName());
            comment.setEmail(commentDto.getEmail());
            comment.setBody(commentDto.getBody());
            Comment updatedComment = commentReponsitory.save(comment);
            return mapToDto(updatedComment);
        } else {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment not found");
        }

    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
    Post post = postReponsitory.findById(postId).orElseThrow(() -> new RuntimeException("Post not found with id " + postId));
    Comment comment = commentReponsitory.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found with id " + commentId));
    if(comment.getPost().getId() == post.getId()){
        commentReponsitory.deleteById(commentId);
    }
    else {
        throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment not found");
    }
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = mapper.map(comment, CommentDto.class);
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);
        return comment;
    }
}

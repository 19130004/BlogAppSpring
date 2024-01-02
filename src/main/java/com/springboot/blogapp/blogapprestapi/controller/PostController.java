package com.springboot.blogapp.blogapprestapi.controller;

import com.springboot.blogapp.blogapprestapi.payload.PostDto;
import com.springboot.blogapp.blogapprestapi.payload.PostResponse;
import com.springboot.blogapp.blogapprestapi.service.PostService;

import com.springboot.blogapp.blogapprestapi.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")

@Tag(
        name=" CRUD REST APIs for posts",
        description = "This controller contains all the operations related to posts"
)
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @SecurityRequirement(
            name = "bearerAuth"
    )
    @Operation(
            summary = "Create a new post",
            description = "This API creates a new post"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Post created successfully"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);

    }

    @GetMapping
    @Operation(
            summary = "Get all posts",
            description = "This API gets all posts"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Get all posts successfully"
    )
    public PostResponse getAllPosts(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.DEFAULT_SORT_ORDER, required = false) String sortOrder) {
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortOrder);
    }
    @SecurityRequirement(
            name = "bearerAuth"
    )
  @Operation(
            summary = "Get post by id",
            description = "This API gets post by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Get post by id successfully"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }
    @SecurityRequirement(
            name = "bearerAuth"
    )
    @Operation(
            summary = "Get posts by category id",
            description = "This API gets posts by category id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Get posts by category id successfully"
    )
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostsByCategoryId(@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.ok(postService.getPostsByCategoryId(categoryId));
    }
    @SecurityRequirement(
            name = "bearerAuth"
    )
    @Operation(
            summary = "Update post by id",
            description = "This API updates post by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Update post by id successfully"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(postService.updatePost(postDto, id));
    }
    @SecurityRequirement(
            name = "bearerAuth"
    )
    @Operation(
            summary = "Delete post by id",
            description = "This API deletes post by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Delete post by id successfully"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") Long id) {
        postService.deletePostById(id);
        return ResponseEntity.ok("Post deleted successfully");
    }
}

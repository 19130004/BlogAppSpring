package com.springboot.blogapp.blogapprestapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(
    name = "posts",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tittle", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "content", nullable = false)
    private String content;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)

    private Set<Comment> comments = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
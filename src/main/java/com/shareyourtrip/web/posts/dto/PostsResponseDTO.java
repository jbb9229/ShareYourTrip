package com.shareyourtrip.web.posts.dto;

import com.shareyourtrip.web.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDTO {

    private Long postId;
    private String title;
    private String content;
    private String author;

    public PostsResponseDTO(Posts entity) {
        this.postId = entity.getPostId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
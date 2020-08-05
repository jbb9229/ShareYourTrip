package com.shareyourtrip.web.posts.dto;

import com.shareyourtrip.web.posts.Posts;
import com.shareyourtrip.web.posts.PostsRead;
import lombok.Getter;

@Getter
public class PostsReadResponseDTO {

    private Long readId;

    private Long postId;

    private Long userNum;

    public PostsReadResponseDTO(PostsRead entity) {
        this.readId = entity.getReadId();
        this.postId = entity.getPostId();
        this.userNum = entity.getUserNum();
    }
}

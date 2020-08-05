package com.shareyourtrip.web.posts.dto;

import com.shareyourtrip.web.posts.Posts;
import com.shareyourtrip.web.posts.PostsRead;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsReadRequestDTO {

    private Long postId;

    private Long userNum;

    @Builder
    public PostsReadRequestDTO(Long postId, Long userNum) {
        this.postId = postId;
        this.userNum = userNum;
    }

    public PostsRead toEntitiy() {
        return PostsRead.builder()
                        .postId(postId)
                        .userNum(userNum)
                        .build();
    }

}

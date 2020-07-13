package com.shareyourtrip.web.posts.dto;

import com.shareyourtrip.web.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsRequestDTO {

    private String title;
    private String content;
    private Long authorId;
    private String author;

    @Builder
    public PostsRequestDTO(String title, String content, Long authorId, String author) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.author = author;
    }

    public Posts toEntitiy() {
        return Posts.builder()
                .title(title)
                .content(content)
                .authorId(authorId)
                .author(author)
                .build();
    }

}

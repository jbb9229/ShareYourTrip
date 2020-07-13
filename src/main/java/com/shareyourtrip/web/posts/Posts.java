package com.shareyourtrip.web.posts;

import com.shareyourtrip.web.model.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Long authorId;

    private String author;

    @Builder
    public Posts(String title, String content, Long authorId, String author) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.author = author;
    }

    public void postUpdate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

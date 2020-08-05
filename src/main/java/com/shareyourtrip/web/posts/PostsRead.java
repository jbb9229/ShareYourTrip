package com.shareyourtrip.web.posts;

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
public class PostsRead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long readId;

    @Column
    private Long postId;

    @Column
    private Long userNum;

    @Builder
    public PostsRead(Long postId, Long userNum) {
        this.postId = postId;
        this.userNum = userNum;
    }

}

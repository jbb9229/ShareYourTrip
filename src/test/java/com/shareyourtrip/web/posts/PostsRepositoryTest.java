package com.shareyourtrip.web.posts;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void initRepositroy() {
        postsRepository.deleteAll();;
    }

    @Test
    public void postCreateAndRead() {
        //given
        String title = "Test Post";
        String content = "Test Content";

        postsRepository.save(Posts.builder()
                                        .title(title)
                                        .content(content)
                                        .authorId(1L)
                                        .author("jbb9229@gmail.com")
                                        .build());

        //whien
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void addBaseTimeEntity() {
        //given
        LocalDateTime now = LocalDateTime.now();
        postsRepository.save(Posts.builder()
                                    .title("Test Title")
                                    .content("Test Content")
                                    .authorId(1L)
                                    .author("jbb")
                                    .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>> \t now \t " + now);
        System.out.println(">>>>>>> \t createDate \t " + posts.getCreatedDate());
        System.out.println(">>>>>>> \t modifiedDate \t " + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);

    }

}
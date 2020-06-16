package com.shareyourtrip.web.posts;

import static org.assertj.core.api.Assertions.assertThat;

import com.shareyourtrip.web.posts.dto.PostsRequestDTO;
import com.shareyourtrip.web.posts.dto.PostsUpdateRequestDTO;
import java.util.Optional;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void repositoryInit() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void postSave() {
        //given
        String title = "Test Title";
        String content = "Test Content";
        PostsRequestDTO requestDTO = PostsRequestDTO.builder()
                                        .title(title)
                                        .content(content)
                                        .author("jbb")
                                        .build();

        String url = "http://localhost:" + port + "/api/posts/save";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDTO, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        Optional<Posts> findPostById = postsRepository.findById(responseEntity.getBody().longValue());
        assertThat(findPostById.get().getTitle()).isEqualTo(title);
        assertThat(findPostById.get().getContent()).isEqualTo(content);
    }

    @Test
    public void postUpdate() throws Exception {
        //given
        Posts savedPost = postsRepository.save(Posts.builder()
                                                    .title("Test Title")
                                                    .content("Test Content")
                                                    .author("jbb")
                                                    .build());

        Long updateId = savedPost.getPostId();
        String expectedTitle = "Update Test Title";
        String expectedContent = "Update Test Content";

        PostsUpdateRequestDTO requestDTO = PostsUpdateRequestDTO.builder()
                                                                .title(expectedTitle)
                                                                .content(expectedContent)
                                                                .build();

        String url = "http://localhost:" + port + "/api/posts/update/" + updateId;

        HttpEntity<PostsUpdateRequestDTO> requestEntity = new HttpEntity<>(requestDTO);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        Optional<Posts> post = postsRepository.findById(updateId);
        assertThat(post.get().getTitle()).isEqualTo(expectedTitle);
        assertThat(post.get().getContent()).isEqualTo(expectedContent);
    }
}
package com.shareyourtrip.web.posts;

import com.shareyourtrip.web.posts.dto.PostsReponseDTO;
import com.shareyourtrip.web.posts.dto.PostsRequestDTO;
import com.shareyourtrip.web.posts.dto.PostsUpdateRequestDTO;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// 선언된 모든 final 필드가 포함된 생성자를 생성
@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long postSave(PostsRequestDTO postsDTO) {
        return postsRepository.save(postsDTO.toEntitiy()).getPostId();
    }

    @Transactional
    public Long postUpdate(Long id, PostsUpdateRequestDTO requestDTO) {
        Posts post = postsRepository.findById(id)
                                        .orElseThrow(() ->
                                            new IllegalArgumentException("해당 게시물을 찾을 수 없습니다. ( ID | " + id + " )"));

        post.postUpdate(requestDTO.getTitle(), requestDTO.getContent());

        return id;
    }

    public PostsReponseDTO findById (Long id) {
        Posts post = postsRepository.findById(id)
            .orElseThrow(() ->
                new IllegalArgumentException("해당 게시물을 찾을 수 없습니다. ( ID | " + id + " )"));

        return new PostsReponseDTO(post);
    }

}

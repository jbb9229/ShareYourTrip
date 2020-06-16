package com.shareyourtrip.web.posts;

import com.shareyourtrip.web.posts.dto.PostsListResponseDTO;
import com.shareyourtrip.web.posts.dto.PostsResponseDTO;
import com.shareyourtrip.web.posts.dto.PostsRequestDTO;
import com.shareyourtrip.web.posts.dto.PostsUpdateRequestDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public PostsResponseDTO findById (Long id) {
        Posts post = postsRepository.findById(id)
            .orElseThrow(() ->
                new IllegalArgumentException("해당 게시물을 찾을 수 없습니다. ( ID | " + id + " )"));

        return new PostsResponseDTO(post);
    }

    // readOnly | 트랜잰션 범위는 유지하되 조회 기능만 남겨두어 조회 속도가 개선
    @Transactional(readOnly = true)
    public List<PostsListResponseDTO> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                              .map(PostsListResponseDTO::new)
                              .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long postId) {
        Posts posts = postsRepository.findById(postId)
                                     .orElseThrow(() ->
                                     new IllegalArgumentException("해당 게시물이 없습니다. | postId = " + postId));
        // JpaRepository의 delete 메소드를 활용 delete(Entity) | deleteById(postId)
        postsRepository.delete(posts);
    }

}

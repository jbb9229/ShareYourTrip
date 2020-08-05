package com.shareyourtrip.web.posts;

import com.shareyourtrip.web.posts.dto.PostsReadRequestDTO;
import com.shareyourtrip.web.posts.dto.PostsReadResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 선언된 모든 final 필드가 포함된 생성자를 생성
@RequiredArgsConstructor
@Service
public class PostsReadService {

    private final PostsReadRepository readRepository;

    @Transactional
    public Long postReadSave(PostsReadRequestDTO requestDTO) {
        List<PostsReadResponseDTO> overlapedReads = readRepository.findByPostIdANDUserNum(requestDTO.getPostId(), requestDTO.getUserNum());

        if (overlapedReads.size() == 0) {
            readRepository.save(requestDTO.toEntitiy());
            return 1L;
        } else {
            return 0L;
        }
    }

}

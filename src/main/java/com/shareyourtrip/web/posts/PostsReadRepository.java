package com.shareyourtrip.web.posts;

import com.shareyourtrip.web.posts.dto.PostsReadResponseDTO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostsReadRepository extends JpaRepository<PostsRead, Long> {

    @Query("SELECT p FROM PostsRead p WHERE p.postId = :postId AND p.userNum = :userNum")
    List<PostsReadResponseDTO> findByPostIdANDUserNum(Long postId, Long userNum);

}

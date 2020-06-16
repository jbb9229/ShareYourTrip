package com.shareyourtrip.web.posts;

import com.shareyourtrip.web.posts.dto.PostsResponseDTO;
import com.shareyourtrip.web.posts.dto.PostsRequestDTO;
import com.shareyourtrip.web.posts.dto.PostsUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 선언된 모든 final 필드가 포함된 생성자를 생성
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/posts/save")
    public Long postSave(@RequestBody PostsRequestDTO requestDTO) {
        return postsService.postSave(requestDTO);
    }

    @PutMapping("/posts/update/{postId}")
    public Long postUpdate(@PathVariable Long postId,
                        @RequestBody PostsUpdateRequestDTO requestDTO) {
        return postsService.postUpdate(postId, requestDTO);
    }

    @GetMapping("/posts/find/{postId}")
    public PostsResponseDTO postFindByID(@PathVariable Long postId) {
        return postsService.findById(postId);
    }

    @DeleteMapping("/posts/delete/{postId}")
    public Long delete(@PathVariable Long postId) {
        postsService.delete(postId);
        return postId;
    }

}

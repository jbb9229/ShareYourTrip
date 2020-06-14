package com.shareyourtrip.web.posts;

import com.shareyourtrip.web.posts.dto.PostsReponseDTO;
import com.shareyourtrip.web.posts.dto.PostsRequestDTO;
import com.shareyourtrip.web.posts.dto.PostsUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 선언된 모든 final 필드가 포함된 생성자를 생성
@RequiredArgsConstructor
@RestController
public class PostsController {

    private final PostsService postsService;

    @PostMapping("/posts/save")
    public Long postSave(@RequestBody PostsRequestDTO requestDTO) {
        return postsService.postSave(requestDTO);
    }

    @PutMapping("/posts/update/{id}")
    public Long postUpdate(@PathVariable Long id,
                        @RequestBody PostsUpdateRequestDTO requestDTO) {
        return postsService.postUpdate(id, requestDTO);
    }

    @GetMapping("/posts/find/{id}")
    public PostsReponseDTO postFindByID(@PathVariable Long id) {
        return postsService.findById(id);
    }

}

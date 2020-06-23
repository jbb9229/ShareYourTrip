package com.shareyourtrip.web.posts;

import com.shareyourtrip.web.config.auth.LoginUser;
import com.shareyourtrip.web.config.auth.dto.SessionUser;
import com.shareyourtrip.web.posts.dto.PostsResponseDTO;
import com.shareyourtrip.web.posts.dto.PostsRequestDTO;
import com.shareyourtrip.web.posts.dto.PostsUpdateRequestDTO;
import com.shareyourtrip.web.system.AWSS3Service;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

// 선언된 모든 final 필드가 포함된 생성자를 생성
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostsApiController {

    private final PostsService postsService;

    @Autowired
    private AWSS3Service awss3Service;

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

    @PostMapping("/posts/fileupload")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> postFileUpload(@LoginUser SessionUser user
                            ,@RequestPart MultipartFile upload) throws Exception {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("imageUrl", awss3Service.upload(upload, user.getEmail()));
        return responseMap;
    }


}

package com.shareyourtrip.web.posts;

import com.shareyourtrip.web.config.auth.LoginUser;
import com.shareyourtrip.web.config.auth.dto.SessionUser;
import com.shareyourtrip.web.posts.dto.PostsReadRequestDTO;
import com.shareyourtrip.web.posts.dto.PostsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    PostsService postsService;

    @Autowired
    PostsReadService readService;

    @GetMapping("/save")
    public String postSave(@LoginUser SessionUser user
                        ,Model model) {
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "posts/posts-save";
    }

    @GetMapping("/list")
    public String postList(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        return "posts/posts-list";
    }

    @GetMapping("/update/{postId}")
    public String postUpdate(@PathVariable Long postId
                            ,Model model
                            ,@LoginUser SessionUser user) {
        PostsResponseDTO responseDTO = postsService.findById(postId);
        model.addAttribute("post", responseDTO);
        if (user != null) {
            model.addAttribute("user", user);
        }

        return "posts/posts-update";
    }

    @GetMapping("/detail/{postId}")
    public String postDetail(@PathVariable Long postId
                            ,Model model
                            ,@LoginUser SessionUser user) {
        PostsResponseDTO responseDTO = postsService.findById(postId);
        boolean isMyPost = responseDTO.getAuthorId().equals(user.getUserNum());
        model.addAttribute("post", responseDTO);
        model.addAttribute("postCheck", isMyPost);
        model.addAttribute("user", user);

        PostsReadRequestDTO requestDTO = new PostsReadRequestDTO().builder()
                                                                  .postId(postId)
                                                                  .userNum(user.getUserNum())
                                                                  .build();

        readService.postReadSave(requestDTO);

        return "posts/posts-detail";
    }

}

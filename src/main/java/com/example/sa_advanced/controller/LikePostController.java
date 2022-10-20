package com.example.sa_advanced.controller;

import com.example.sa_advanced.controller.response.ResponseDto;
import com.example.sa_advanced.service.LikePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Validated
@RequiredArgsConstructor
@RestController
public class LikePostController {
    private final LikePostService likePostService;

    @PostMapping(value = "/api/auth/like/{post_id}")
    public ResponseDto<?> likePost(@PathVariable Long post_id,
                                   HttpServletRequest request) {
        return likePostService.likePost(post_id, request);

    }
}

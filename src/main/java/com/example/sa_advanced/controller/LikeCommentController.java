package com.example.sa_advanced.controller;

import com.example.sa_advanced.controller.response.ResponseDto;
import com.example.sa_advanced.service.LikeCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Validated
@RequiredArgsConstructor
@RestController
public class LikeCommentController {
    private final LikeCommentService likeCommentService;

    @PostMapping(value = "/api/auth/comment/like/{comment_id}")
    public ResponseDto<?> likePost(@PathVariable Long comment_id,
                                   HttpServletRequest request) {
        return likeCommentService.likeComment(comment_id, request);

    }
}

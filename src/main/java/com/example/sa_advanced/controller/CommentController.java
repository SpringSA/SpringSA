package com.example.sa_advanced.controller;

import com.example.sa_advanced.controller.request.CommentRequestDto;
import com.example.sa_advanced.controller.response.ResponseDto;
import com.example.sa_advanced.domain.UserDetailsImpl;
import com.example.sa_advanced.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentController {

    private final CommentService commentService;


    //댓글 작성
    @PostMapping(value = "/auth/comment")
    //@@Authentication principal 사용하면 쉬울수도?
    public ResponseDto<?> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(userDetails, requestDto);
    }

    //댓글 가져오기
    @GetMapping(value = "/comment/{id}")
    public ResponseDto<?> showComments(@PathVariable Long id){
        return commentService.showComments(id);
    }

    //댓글 수정(권한 있는지 check => auth)
    @PutMapping(value="/auth/comment/{id}")
    public ResponseDto<?> updateComments(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @PathVariable Long id,
                                         @RequestBody CommentRequestDto commentRequestDto ){
        return commentService.updateComments(userDetails,id, commentRequestDto);
    }

    //댓글 삭제
    @DeleteMapping(value="/auth/comment/{id}")
    public ResponseDto<?> deleteComments(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @PathVariable Long id,
                                         @RequestBody CommentRequestDto commentRequestDto){
        return commentService.deleteComments(userDetails,id, commentRequestDto);
    }

}

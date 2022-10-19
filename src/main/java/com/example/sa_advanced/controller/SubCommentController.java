package com.example.sa_advanced.controller;

import com.example.sa_advanced.controller.request.SubCommentRequestDto;
import com.example.sa_advanced.controller.response.ResponseDto;
import com.example.sa_advanced.service.SubCommentService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * SubCommentController
 */
@Builder
@Validated
@RequiredArgsConstructor
@RestController
public class SubCommentController {

    private final SubCommentService subCommentService;

    @RequestMapping(value = "/api/auth/subcomment", method = RequestMethod.POST)
    public ResponseDto<?> createReComment(@RequestBody SubCommentRequestDto requestDto,
                                          HttpServletRequest request) {
        return subCommentService.createReComment(requestDto, request);
    }

    @RequestMapping(value = "/api/subcomment/{id}", method = RequestMethod.GET)
    public ResponseDto<?> getAllSubComments(@PathVariable Long id){
    return subCommentService.getAllSubcommentsByComment(id);
    }

}

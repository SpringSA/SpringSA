package com.example.sa_advanced.controller;


import com.example.sa_advanced.controller.response.ResponseDto;
import com.example.sa_advanced.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * MyPageController
 */
@Validated
@RequiredArgsConstructor
@RestController
public class MyPageController {
    private final MyPageService myPageService;

    @RequestMapping(value = "/api/auth/mypage", method= RequestMethod.GET)
    public ResponseDto<?> getMyPage(HttpServletRequest request) {
        return myPageService.getMyPage(request);
    }
}

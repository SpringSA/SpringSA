package com.example.sa_advanced.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeCommentResponseDto {
    private Long id;
    private String author; // mypage 추가 2022-10-20 - 오후 3시 53분
    private String content; // mypage 추가 2022-10-20 - 오후 3시 53분
    private Long likeCount; // mypage 추가 2022-10-20 - 오후 3시 53분
    private String likeOwner;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

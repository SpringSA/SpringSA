package com.example.sa_advanced.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikePostResponseDto {
    private Long id;

    @JsonIgnore
    private String likeOwner; // likeOnwer likecomment & likepost ignore 처리함니다 2022-10-20- 오후 10시25분

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String title; // mypage 추가 2022-10-20 오후 4시 31분
    private Long likeCount; // mypage 추가 2022-10-20 오후 4시 31분
    private String member; // mypage 추가 2022-10-20 오후 4시 31분
    private String content; // mypage 추가 2022-10-20 오후 4시 31분
}

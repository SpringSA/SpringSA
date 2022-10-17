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
public class PostResponseDto {
    private Long id;
    private String member;
    private String content;
//    private String comments;
    private String title;
//    private String imageUrl;
//    private String likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

package com.example.sa_advanced.controller.response;

import com.example.sa_advanced.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private Member member;
    private String content;
    private String title;
    private List<CommentResponseDto> commentResponseDtoList;
//    private String imageUrl;
//    private String likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

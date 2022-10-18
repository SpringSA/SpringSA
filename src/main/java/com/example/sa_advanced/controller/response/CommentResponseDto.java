package com.example.sa_advanced.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {

    private Long id;
    private String content;
    private String author;
    private List<LikeCommentResponseDto> likeCommentResponseDtoList;
    private int likeCount =0;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    //default
    private boolean editCheck;

}

package com.example.sa_advanced.controller.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubCommentResponseDto {
    private Long id;
    private String author;
    private String subComment;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}

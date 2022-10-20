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
public class LikePostResponseDto {
    private Long id;
    private String likeOwner;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

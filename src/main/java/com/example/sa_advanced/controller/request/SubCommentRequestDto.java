package com.example.sa_advanced.controller.request;

import lombok.*;

@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubCommentRequestDto {
    private Long commentId;
    private String subComment;
}

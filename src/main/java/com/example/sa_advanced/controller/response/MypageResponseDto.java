package com.example.sa_advanced.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * MypageResponseDto
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MypageResponseDto {

    private List<PostResponseDto> myPosts;
    private List<CommentResponseDto> myComments;
    private List<LikeCommentResponseDto> likeComments; // 좋아요한 comment 내용 추가함 2022-10-20- 오후 4시 7분
    private List<LikePostResponseDto> likePosts; // 좋아요한 post 내용 추가함  2022-10-20 - 오후 4시 40분


}

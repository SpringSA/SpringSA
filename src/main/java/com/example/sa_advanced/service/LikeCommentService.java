package com.example.sa_advanced.service;

import com.example.sa_advanced.controller.response.CommentResponseDto;
import com.example.sa_advanced.controller.response.PostResponseDto;
import com.example.sa_advanced.controller.response.ResponseDto;
import com.example.sa_advanced.domain.*;
import com.example.sa_advanced.jwt.TokenProvider;
import com.example.sa_advanced.repository.CommentRepository;
import com.example.sa_advanced.repository.LikeCommentRepository;
import com.example.sa_advanced.repository.LikePostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class LikeCommentService {
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final TokenProvider tokenProvider;
    @Transactional
    public ResponseDto<?> likeComment(Long comment_id, HttpServletRequest request){
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        //
        Comment comment = commentRepository.findById(comment_id).orElse(null);
        if(comment == null) return ResponseDto.fail("NOT_FOUND","댓글을 찾을 수 없습니다.");

        LikeComment like = null;
        for(LikeComment likeCheck : comment.getLikeComments()){
            if(likeCheck.getMember().getId()==member.getId()) {
                like=likeCheck;
                break;
            }
        }
        if(like==null){
            // 좋아요
            like = new LikeComment();
            like.setMember(member);
            like.setComment(comment);
            comment.getLikeComments().add(like);
            likeCommentRepository.save(like);
        }
        else {
            // 좋아요 취소
            comment.getLikeComments().remove(like);
//            member.getLikeComments().remove(like);
            likeCommentRepository.delete(like);
        }


        return ResponseDto.success(
                CommentResponseDto.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .author(comment.getMember().getUsername())
                        .likeCount(comment.countLike())
                        .createdAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .build()
        );
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}

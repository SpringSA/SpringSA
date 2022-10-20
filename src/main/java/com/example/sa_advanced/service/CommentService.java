package com.example.sa_advanced.service;

import com.example.sa_advanced.controller.request.CommentRequestDto;
import com.example.sa_advanced.controller.response.CommentResponseDto;
import com.example.sa_advanced.controller.response.ResponseDto;
import com.example.sa_advanced.domain.Comment;
import com.example.sa_advanced.domain.Post;
import com.example.sa_advanced.domain.UserDetailsImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.sa_advanced.repository.CommentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Data
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;


    @Transactional
    public ResponseDto<?> createComment(UserDetailsImpl userDetails, CommentRequestDto requestDto) {

        //@Authentication principal => 로그인 완료돼서 건너왔으면
        //User type으로 Authentication은 이미 존재해야 되는데 NULL일 수 있나? => Token 만료 시간이 10초면?
        if (userDetails == null) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }


        //Post 있어야 comment 작성 가능 => Post NULL일 경우 Error
        Post post = postService.isPresentPost(requestDto.getPostId());
        if (post == null) {
            return ResponseDto.fail("POST_NOT_FOUND", "해당 포스트가 존재하지 않습니다.");
        }


        //post, member 다 존재? => comment 저장 가능
        //comment builder 쓰자
        /**
         *
         * 혜수님~ like_count 완료하시면 builder에 추가해주세요:)
         *
         */
        Comment comment = Comment.builder()
                .member(userDetails.getMember())
                .post(post)
                .content(requestDto.getContent())

                .build();
        commentRepository.save(comment);

        //Response => id, comment, author, createdAt, modifiedAt, editCheck
        return ResponseDto.success(
                CommentResponseDto.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .author(userDetails.getMember().getNickname())
                        .createdAt(comment.getCreatedAt())
                        .modifiedAt((comment.getModifiedAt()))
                        //editCheck boolean return
                        .editCheck(comment.checkEdit(requestDto))
                        .build()
        );
    }

    //포스트에 해당하는 댓글 가져오기(읽기 전용)
    @Transactional(readOnly = true)
    public ResponseDto<?> showComments(Long postId) {

        //Post 있어야 comment 읽기 가능 => Post NULL일 경우 Error
        Post post = postService.isPresentPost(postId);
        if (post == null) {
            return ResponseDto.fail("NOT_FOUND", "해당 포스트가 존재하지 않습니다.");
        }

        //post 있으면 comment 다 가져오자
        List<Comment> comments = commentRepository.findAllByPost(post);
        List<CommentResponseDto> commentsList = new ArrayList<>();

        for (Comment comment : comments) {
            commentsList.add(
                    CommentResponseDto.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .author(comment.getMember().getNickname())
                        .createdAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .build()
            );

        }
        return ResponseDto.success(commentsList);
    }

    //코멘트 update 기능
    @Transactional
    public ResponseDto<?> updateComments(UserDetailsImpl userDetails, Long id, CommentRequestDto commentRequestDto) {

        //Token 만료 시간이 짧으면 No Userdetails
        if (userDetails == null) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        //Post가 Null이면? => comment update 불가
        Post post = postService.isPresentPost(commentRequestDto.getPostId());
        if (post == null) {
            return ResponseDto.fail("NOT_FOUND", "해당 게시글이 존재하지 않습니다.");
        }

        //comment가 Null이면? => comment update 불가
        Comment comment = isPresentComment(id);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "해당 코멘트가 존재하지 않습니다.");
        }

        //comment update(set => service layer or domain layer?)
        boolean editCheck = comment.checkEdit(commentRequestDto);


        comment.setContent(commentRequestDto.getContent());
        System.out.println("comment changed to : " + comment.getContent());
        return ResponseDto.success(
            CommentResponseDto.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .author(comment.getMember().getNickname())
                    .createdAt(comment.getCreatedAt())
                    .modifiedAt(comment.getModifiedAt())
                    .editCheck(editCheck)
                    .build()
        );

    }


    @Transactional(readOnly = true) //for finding datas purpose only
    public Comment isPresentComment(Long id) {
        Optional<Comment> byId = commentRepository.findById(id);
        return byId.orElse(null);
    }

    @Transactional
    public ResponseDto<?> deleteComments(UserDetailsImpl userDetails, Long id, CommentRequestDto commentRequestDto) {

        //Token 만료 시간이 짧으면 No Userdetails
        if (userDetails == null) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        //Post가 Null이면? => comment update 불가
        Post post = postService.isPresentPost(commentRequestDto.getPostId());
        if (post == null) {
            return ResponseDto.fail("NOT_FOUND", "해당 게시글이 존재하지 않습니다.");
        }

        //comment가 Null이면? => comment update 불가
        Comment comment = isPresentComment(id);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "해당 코멘트가 존재하지 않습니다.");
        }

        commentRepository.delete(comment);
        return ResponseDto.success("deleted successfully");
    }

}

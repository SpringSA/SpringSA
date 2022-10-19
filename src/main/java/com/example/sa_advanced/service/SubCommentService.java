package com.example.sa_advanced.service;

import com.example.sa_advanced.controller.request.SubCommentRequestDto;
import com.example.sa_advanced.controller.response.ResponseDto;
import com.example.sa_advanced.controller.response.SubCommentResponseDto;
import com.example.sa_advanced.domain.Comment;
import com.example.sa_advanced.domain.Member;
import com.example.sa_advanced.domain.SubComment;
import com.example.sa_advanced.jwt.TokenProvider;
import com.example.sa_advanced.repository.CommentRepository;
import com.example.sa_advanced.repository.LikePostRepository;
import com.example.sa_advanced.repository.SubCommentRepository;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubCommentService {
    private final CommentRepository commentRepository;
    private final SubCommentRepository subCommentRepository;


    private final TokenProvider tokenProvider;
    private final PostService postService;
    private final CommentService commentService;

    /**
     * 대댓글 등록
     * @param requestDto
     * @param request
     * @return
     * @author doosan
     */
    @Transactional
    public ResponseDto<?> createReComment(SubCommentRequestDto requestDto, HttpServletRequest request) {
        // 로그인 유효성 체크
        if(null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }
        //Token 유효성 체크
        Member member = validateMember(request);
        if(null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Comment comment = commentService.isPresentComment(requestDto.getCommentId());

        /** comment 유효성 체크 */
        if(null==comment) {
            return ResponseDto.fail("NOT_FOUND","존재하지 않는 코멘트 ID입니다.");
        }

        /** builder 패턴 사용, member,comment,subcomment  */
        SubComment subComment = SubComment.builder()
                .member(member)
                .comment(comment)
                .subComment(requestDto.getSubComment())
                .build();

        System.out.println(subComment.getComment()); //comment 찍어보기

        subCommentRepository.save(subComment); //subComment 저장

        return ResponseDto.success(        // 유효성 통과했을경우
                SubCommentResponseDto.builder()
                        .id(subComment.getId())          // ID
                        .author(subComment.getMember().getNickname()) // 작성자
                        .subComment(subComment.getSubComment()) // 대댓글
                        .createdAt(subComment.getCreatedAt()) // 생성일
                        .modifiedAt(subComment.getCreatedAt()) // 수정일
                        .build());
    }


    /**
     * 대댓글 조회
     * @param commentId
     * @return
     * @author doosan
     */

    public ResponseDto<?> getAllSubcommentsByComment(Long commentId) {
        Comment comment = commentService.isPresentComment(commentId);
        if(null == comment) {
            return ResponseDto.fail("NOT_FOUND","존재하지 않는 댓글 ID 입니다.");
        }
        List<SubComment> subCommentList = subCommentRepository.findAllByComment(comment);
        List<SubCommentResponseDto> subCommentResponseDtoList = new ArrayList<>();
        //대댓글 조회 하고  만약 comment_like 추가 할거면 여기 추가해야함
        for(SubComment subCommentEach : subCommentList) {
            subCommentResponseDtoList.add(
                SubCommentResponseDto.builder()
                        .id(subCommentEach.getId()) //id
                        .author(subCommentEach.getMember().getNickname()) // 작성자
                        .subComment(subCommentEach.getSubComment()) //  대댓글
                        .createdAt(subCommentEach.getCreatedAt()) // 생성일
                        .modifiedAt(subCommentEach.getModifiedAt()) // 수정일
                        .build()
            );
        }
            return ResponseDto.success(subCommentResponseDtoList);
    }


    @Transactional(readOnly = true)
    public SubComment isPresentSubComment(Long id) {
        Optional<SubComment> optionalSubComment = subCommentRepository.findById(id);
        return optionalSubComment.orElse(null);
    }
    @Transactional /**  Member 유효성 검사*/
    public Member validateMember(HttpServletRequest request) {
        if(!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) { // RefreshToken 값있는지 확인
            return null;
        }
        return tokenProvider.getMemberFromAuthentication(); // Refresh_Token 있으면 멤버 인증정보 넘김
    }
}

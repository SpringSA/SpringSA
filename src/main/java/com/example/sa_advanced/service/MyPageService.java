package com.example.sa_advanced.service;

import com.example.sa_advanced.controller.response.*;
import com.example.sa_advanced.domain.*;
import com.example.sa_advanced.jwt.TokenProvider;
import com.example.sa_advanced.repository.CommentRepository;
import com.example.sa_advanced.repository.LikeCommentRepository;
import com.example.sa_advanced.repository.LikePostRepository;
import com.example.sa_advanced.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MyPageService {
    private final PostRepository postRepository;
    private final LikePostRepository likePostRepository;

    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;

    // CommentRepository 추가해야함
    private final TokenProvider tokenProvider;

    public ResponseDto<?> getMyPage(HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        // 헤더에 엑세스 토큰 없으면 "MEMBER_NOT_FOUND" , "로그인이 필요합니다." 노출
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        // Member 인증통과 안되면, INVALID_TOKEN , "Token이 유효하지 않습니다." 노출
        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        /**
         * 내가 작성한 댓글
         */
        List<Comment> myCommentList = commentRepository.findAllByMember(member);
        List<CommentResponseDto> myCommentResponseDtoList = new ArrayList<>();

        for(Comment comment : myCommentList) {
            myCommentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .author(comment.getMember().getNickname())
                            .content(comment.getContent())
//                            .likeCount(likePostRepository.countAllByCommentId(comment.getId()))
                            .likeCount((int) comment.countLike()) // 2022-10-20 오후 10:01
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );
        }



        /**
         * 내가 작성한 게시글
         */
        List<Post> myPostList = postRepository.findAllByMember(member);  // postRepository에서 findAllByMember로 가저온 정보를 myPostList에 list 형식으로 담아서
        List<PostResponseDto> myPostResponseDtoList = new ArrayList<>(); // 배열의 크기를 알수 없을때 사용하면 좋은 ArrayList를 사용해서 myPostResponseDtoList 를 선언하고


//        List<Post> list = member.getLikePosts();
        for (Post post : myPostList) { // 반복문으로 돌면서 myPostResponseDtoList 에 .add 로 넣어준다.
            myPostResponseDtoList.add(
                    PostResponseDto.builder() // builder 패턴으로 필요한것을 선별적으로 넣어준다.
                            .id(post.getId())         // id
                            .title(post.getTitle())   // title
                            .member(post.getMember().getNickname())  // nickname
                            .content(post.getContent())  // content
                            .likeCount(likePostRepository.countAllByPostId(post.getId())) // likeCount 추가함 2022-10-20- 오후 3시 20분
//                            .likeCount(post.countLike())
                            .createdAt(post.getCreatedAt())  // 생성일
                            .modifiedAt(post.getModifiedAt())  // 수정일
                            // comment_cnt 추가해야함 cuz scheduller
                            .build()
            );
        }

        /** 내가 좋아요 누른 게시글 */
        List<LikePost> likePostList = likePostRepository.findAllByMember(member);
        List<LikePostResponseDto> likePostResponseDtoList = new ArrayList<>();

        for(LikePost likePost : likePostList ) {
            likePostResponseDtoList.add(
                      LikePostResponseDto.builder()
                              .id(likePost.getPost().getId())
                           //   .likeOwner(likePost.getPost().get) like owner  likeOnwer likecomment & likepost ignore 처리함니다 2022-10-20- 오후 10시25분
                              .title(likePost.getPost().getTitle())
                              .member(likePost.getPost().getMember().getNickname())
                              .content(likePost.getPost().getContent())
                              .likeCount((long) likePostRepository.countAllByPostId(likePost.getPost().getId()))
                              .createdAt(likePost.getPost().getCreatedAt())
                              .modifiedAt(likePost.getPost().getModifiedAt())
                              .build()
            );
        }

        /** 내가 좋아요 누른 댓글 */
        List<LikeComment> likeCommentList = likeCommentRepository.findAllByMember(member);
        List<LikeCommentResponseDto> likeCommentResponseDtoList = new ArrayList<>();

        for (LikeComment likeComment : likeCommentList) {
            likeCommentResponseDtoList.add(
                    LikeCommentResponseDto.builder()
                            .id(likeComment.getComment().getId())
                            .author(likeComment.getComment().getMember().getNickname())
                            .content(likeComment.getComment().getContent())
//                            .likeCount(likeCommentRepository.countAllByCommentId(likeComment.getComment().getId())) // likeCommentRepository 상수 추가 2022-10-20 - 오후 4시 12분
                            .likeCount((long) likeComment.getComment().countLike())
                            .createdAt(likeComment.getComment().getCreatedAt())
                            .modifiedAt(likeComment.getComment().getModifiedAt())
                            .build()
            );
        }


        return ResponseDto.success(
                MypageResponseDto.builder()
                        .myPosts(myPostResponseDtoList)               // 내가 작성한 게시글
                        .myComments(myCommentResponseDtoList)        // 내가 작성한 댓글 추가해야함 2022-10-20-오후 4시 35분
                        .likeComments(likeCommentResponseDtoList)     // 좋아요 누른 댓글 .likeComments 추가함 2022-10-20-오후 4시00분
                        .likePosts(likePostResponseDtoList)           //  좋아요 누른 게시글 추가함 2022-10-20-오후 4시 40분
                        .build()
        );
    }

    /**
     * Refresh-Token으로 Member 인증
     */
    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
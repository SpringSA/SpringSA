package com.example.sa_advanced.repository;

import com.example.sa_advanced.domain.LikePost;
import com.example.sa_advanced.domain.Member;
import com.example.sa_advanced.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    List<LikePost> findAllByPost(Post post);

    /**  countAllByPostId 로 포스트 id 찾아서 myPageServcie 페이지에서 내가 작성한 게시글 좋아요 찾음 */
    int countAllByPostId(Long postId);
    List<LikePost> findAllByMember(Member member); // mypage 추가 2022-10-20- 오후 4시 16분
 //    int countAllByCommentId(Long commentId); // mypage 추가 2022-10-20 오후 4시 52분

    Optional<LikePost> findByMemberAndPost(Member member, Post post);
}
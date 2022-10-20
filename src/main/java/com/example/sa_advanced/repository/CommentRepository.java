package com.example.sa_advanced.repository;

import com.example.sa_advanced.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);

    List<Comment> findAllByMember(Member member); //mypage 추가 2022-10-20 오후 4시 45분
    //findAllByMember
}

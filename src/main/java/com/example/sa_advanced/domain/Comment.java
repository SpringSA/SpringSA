package com.example.sa_advanced.domain;


import com.example.sa_advanced.controller.request.CommentRequestDto;
import com.example.sa_advanced.controller.response.CommentResponseDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
public class Comment extends Timestamped{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", nullable = false)
    private Post post;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="comment_id",nullable = false) // 대댓글 id수정 comment_id -> parent_id 로 변경함
//    private Comment comment;
//
    @Column(nullable = false)
    private String content;

//    @JsonManagedReference
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<LikeComment> likeComments = new ArrayList<>();


    public boolean checkEdit(CommentRequestDto requestDto) {
        if (this.content.equals(requestDto.getContent())){
            //edit 안했으면 editCheck = false
            return false;
        } else{
            //edit 했으면 editcheck = true
            return true;
        }
    }
    public int countLike(){
        return getLikeComments().size();
    }
}

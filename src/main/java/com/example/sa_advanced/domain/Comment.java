package com.example.sa_advanced.domain;


import com.example.sa_advanced.controller.request.CommentRequestDto;
import com.example.sa_advanced.controller.response.CommentResponseDto;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
public class Comment extends Timestamped{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String content;


    public boolean checkEdit(CommentRequestDto requestDto) {
        if (this.content.equals(requestDto.getContent())){
            //edit 안했으면 editCheck = false
            return false;
        } else{
            //edit 했으면 editcheck = true
            return true;
        }
    }
    // test2
    /**
     * 혜수님~ 여기도 하실 때 주석 제거하고 사용해주세요:)
     */
//    @Column(nullable = false)
//    private int like_count;
}

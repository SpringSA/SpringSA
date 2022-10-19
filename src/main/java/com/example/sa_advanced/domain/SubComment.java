package com.example.sa_advanced.domain;

import com.example.sa_advanced.controller.request.SubCommentRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * 대댓글
 */
@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SubComment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** member_id 조인 */
    @JoinColumn(name = "member_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    /** comment_id 조인 */
    @JoinColumn(name = "comment_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    /** 대댓글 */
    @Column(nullable = false)
    private String subComment;

    public void update(SubCommentRequestDto subCommentRequestDto) {
        this.subComment = subCommentRequestDto.getSubComment();
    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }
}

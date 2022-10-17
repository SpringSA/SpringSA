package com.example.sa_advanced.domain;


import com.example.sa_advanced.controller.request.PostRequestDto;
import io.swagger.annotations.Info;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Post extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;


//    @Column(nullable = false)
//    private String imageUrl;

//    @Column(nullable = false)
//    private Integer likeCount;



    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }




}

package com.example.sa_advanced.domain;


import com.example.sa_advanced.controller.request.PostRequestDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.Info;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Post extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikePost> likePosts = new ArrayList<>();


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
    public int countLike(){
        return getLikePosts().size();
    }



}

package com.example.sa_advanced.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LikePost extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;

    @JsonManagedReference
    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member member;

}

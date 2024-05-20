package org.example.manageproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private long id;

    @ManyToOne //많은 member->하나의 post
    @JoinColumn(name="member_id")
    private Member member;

    private String title;
    private String content;

    @CreationTimestamp
    private LocalDateTime postdate;

    @OneToMany(mappedBy = "post") // 하나의 post -> 많은 comments
    private List<Comment> comments = new ArrayList<>();

}

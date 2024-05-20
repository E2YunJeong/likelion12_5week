package org.example.manageproject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.*;


@Entity // 테이블과 엔티티 매핑
@Getter //lombok 사용, annotation 사용하면 일일이 안 만들어도 됨
@Setter
public class Member {
        @Id // 기본키 지정
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        // 기본키의 값을 생성할 전략을 지정
        @Column(name="member_id")
        private Long id;

        private String name;
        private String email;

        @CreationTimestamp // 현재 시간으로 타임스탬프 생성
        private LocalDateTime registerDate;

        @OneToMany(mappedBy="member") // 일대다 관계, member에 의해 매핑, 멤버에서 포스트는 일대다 관계
        private List<Post> posts = new ArrayList<>();

        @OneToMany(mappedBy="member") //멤버에서 코멘트는 일대다 관계
        private List<Comment> comments = new ArrayList<>();

}

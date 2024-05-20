package org.example.manageproject.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.manageproject.domain.Member;
import org.example.manageproject.domain.Post;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {
    private long id;
    private Member member;
    private String title;
    private String content;
    private LocalDateTime postdate;

    public static PostDto from(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(postDto.getId());
        postDto.setMember(postDto.getMember());
        postDto.setContent(postDto.getContent());
        postDto.setTitle(postDto.getTitle());
        postDto.setPostdate(postDto.getPostdate());
        return postDto;
    }
}

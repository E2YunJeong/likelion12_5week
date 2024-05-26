package org.example.manageproject.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.manageproject.domain.Member;
import org.example.manageproject.domain.Post;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {
    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private LocalDateTime postdate;

    public static PostDto from(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setTitle(post.getTitle());
        postDto.setPostdate(post.getPostdate());
        if(post.getMember()!=null){
            postDto.setMemberId(post.getMember().getId());
        }
        return postDto;
    }
}

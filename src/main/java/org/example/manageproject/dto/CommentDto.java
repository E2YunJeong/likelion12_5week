package org.example.manageproject.dto;

import lombok.*;
import org.example.manageproject.domain.Comment;
import org.example.manageproject.domain.Member;
import org.example.manageproject.domain.Post;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private long id;
    private Post post;
    private Member member;
    private String content;
    private LocalDateTime commentDate;

    public static CommentDto from(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setPost(comment.getPost());
        commentDto.setMember(comment.getMember());
        commentDto.setContent(comment.getContent());
        commentDto.setCommentDate(comment.getCommentDate());

        return commentDto;
    }
}

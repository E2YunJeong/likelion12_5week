package org.example.manageproject.dto;

import lombok.*;
import org.example.manageproject.domain.Comment;
import org.example.manageproject.domain.Member;
import org.example.manageproject.domain.Post;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private Long postId;
    private Long memberId;
    private String content;
    private LocalDateTime commentDate;

    public static CommentDto from(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setCommentDate(comment.getCommentDate());

        if (comment.getMember() != null){
            commentDto.setMemberId(comment.getMember().getId());
        }
        if (comment.getPost() != null){
            commentDto.setPostId(comment.getPost().getId());
        }

        return commentDto;
    }
}

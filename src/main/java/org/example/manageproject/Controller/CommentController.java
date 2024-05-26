package org.example.manageproject.Controller;

import org.example.manageproject.Service.CommentService;
import org.example.manageproject.Service.MemberService;
import org.example.manageproject.Service.PostService;
import org.example.manageproject.domain.Comment;
import org.example.manageproject.domain.Member;
import org.example.manageproject.domain.Post;
import org.example.manageproject.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;
    private final MemberService memberService;
    private final PostService postService;

    @Autowired
    public CommentController(CommentService commentService, MemberService memberService, PostService postService) {
        this.commentService = commentService;
        this.memberService = memberService;
        this.postService = postService;
    }

    // 코멘트 작성
    @PostMapping
    public ResponseEntity<CommentDto> registerComment(@RequestBody CommentDto commentDto){
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());

        Optional<Member> memberOptional = memberService.getMemberById(commentDto.getMemberId());
        Optional<Post> postOptional = postService.getPostById(commentDto.getPostId());

        if (memberOptional.isPresent() && postOptional.isPresent()){
            Member member = memberOptional.get();
            Post post = postOptional.get();

            comment.setMember(member);
            comment.setPost(post);
        }
        else{
            throw new RuntimeException("해당 멤버 또는 포스트가 존재하지 않습니다: " + commentDto.getMemberId() + commentDto.getPostId());
        }

        Comment registeredComment = commentService.registerComment(comment);
        return ResponseEntity.ok(CommentDto.from(registeredComment));
    }

    /*-------------------------------------------------------------------------*/
    // 코멘트 조회
    // 모든 코멘트 조회
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments(){
        List<Comment> comments = commentService.getAllComments();
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments){
            CommentDto dto = CommentDto.from(comment);
            commentDtos.add(dto);
        }
        return ResponseEntity.ok(commentDtos);
    }

    // 특정 코멘트 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(name = "id") Long id){
        Optional<Comment> commentOptional = commentService.getCommentById(id);
        if (commentOptional.isPresent()){
            CommentDto dto = CommentDto.from(commentOptional.get());
            return ResponseEntity.ok(dto);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    /*-------------------------------------------------------------------------*/

    // 코멘트 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable(name = "id") Long id){
        commentService.deleteCommentById(id);
        return ResponseEntity.noContent().build();
    }

    // 코멘트 수정
    @PatchMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(name = "id") Long id, @RequestBody CommentDto commentDto){
        try{
            Comment updateComment = commentService.updateComment(id, commentDto);
            return ResponseEntity.ok(CommentDto.from(updateComment));
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}

package org.example.manageproject.Service;

import org.example.manageproject.Repository.CommentRepository;
import org.example.manageproject.domain.Comment;
import org.example.manageproject.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // Create
    @Transactional
    public Comment registerComment(Comment comment) {
        return commentRepository.save(comment);
    }
    // Transactional이란?
    // 바로 이전 커밋~최종적으로 커밋을 날리기까지의 주기
    // 어느 한 곳에서 에러 발생 -> 모든 것을 롤백시키도록 하는 것
    // @Transactional : 해당 어노테이션이 적용되는 메소드를 하나의 트랜잭션으로 묶어주는 역할
    // 어노테이션 사용 -> try-catch문 사용할 필요X (코드 중복 X)
    // 클래스 위에 어노테이션 붙임 -> 클래스에 속한 모든 메소드에 어노테이션 적용됨

    /*-------------------------------------------------------------------------*/
    // Read
    // 모든 코멘트 조회
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    // 특정 코멘트 조회
    public Optional<Comment> getCommentById(Long id){return commentRepository.findById(id);}
    /*-------------------------------------------------------------------------*/

    // Delete
    @Transactional
    public void deleteCommentById(Long id){
        commentRepository.deleteById(id);
    }

    // Update
    @Transactional
    public Comment updateComment(Long id, CommentDto commentDto){
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()){
            Comment comment = commentOptional.get();
            if (commentDto.getContent() != null){
                comment.setContent(comment.getContent());
            }
            return commentRepository.save(comment);
        }
        else{
            throw new RuntimeException("Comment not found with id " + id);
        }
    }
}

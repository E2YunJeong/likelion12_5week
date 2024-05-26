package org.example.manageproject.Controller;

import org.example.manageproject.Service.MemberService;
import org.example.manageproject.Service.PostService;
import org.example.manageproject.domain.Member;
import org.example.manageproject.domain.Post;
import org.example.manageproject.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final MemberService memberService;

    @Autowired
    public PostController(PostService postService, MemberService memberService) {
        this.postService = postService;
        this.memberService = memberService;
    }

    // 게시물 작성
    @PostMapping
    public ResponseEntity<PostDto> registerPost(@RequestBody PostDto postDto){
        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        Optional<Member> memberOptional = memberService.getMemberById(postDto.getMemberId());

        if(memberOptional.isPresent()){
            Member member = memberOptional.get();
            post.setMember(member);
        }
        else {
            throw new RuntimeException("해당 멤버가 존재하지 않습니다: " + postDto.getMemberId());
        }

        Post registeredPost = postService.registerPost(post);
        return ResponseEntity.ok(PostDto.from(registeredPost));
    }

    /*-------------------------------------------------------------------------*/
    // 조회
    // 모든 게시물 조회
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(){
        List<Post> posts = postService.getAllPosts();
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts){
            PostDto dto = PostDto.from(post);
            postDtos.add(dto);
        }
        return ResponseEntity.ok(postDtos);
    }

    // ID로 게시물 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id){
        Optional<Post> postOptional = postService.getPostById(id);
        if(postOptional.isPresent()){
            PostDto dto = PostDto.from(postOptional.get());
            return ResponseEntity.ok(dto);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    /*-------------------------------------------------------------------------*/

    // 게시물 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable(name = "id") Long id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    // 게시물 정보 수정
    @PatchMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable(name = "id") Long id, @RequestBody PostDto postDto){
        try{
            Post updatePost = postService.updatePost(id, postDto);
            return ResponseEntity.ok(PostDto.from(updatePost));
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}

package org.example.manageproject.Service;

import org.example.manageproject.Repository.PostRepository;
import org.example.manageproject.domain.Post;
import org.example.manageproject.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // Create
    @Transactional
    public Post registerPost(Post post) {
        return postRepository.save(post);
    }

    /*-------------------------------------------------------------------------*/
    // Read
    // 전체 조회
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // 특정 데이터(id)만 조회
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }
    /*-------------------------------------------------------------------------*/

    // Delete
    @Transactional
    public void deletePost(Long id){
        postRepository.deleteById(id);
    }

    // Update
    @Transactional
    public Post updatePost(Long id, PostDto postDto){
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()){
            Post post = postOptional.get();
            if (postDto.getContent() != null){
                post.setContent(postDto.getContent());
            }
            if (postDto.getTitle() != null){
                post.setTitle(postDto.getTitle());
            }
            return postRepository.save(post);
        }
        else{
            throw new RuntimeException(("Post not found with id " + id));
        }
    }
}

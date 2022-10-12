package org.example.springbootaws.service.posts;

import lombok.RequiredArgsConstructor;
import org.example.springbootaws.domain.posts.Posts;
import org.example.springbootaws.domain.posts.PostsRepository;
import org.example.springbootaws.web.dto.PostsListResponseDto;
import org.example.springbootaws.web.dto.PostsResponseDto;
import org.example.springbootaws.web.dto.PostsSaveRequestDto;
import org.example.springbootaws.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not Found. id="+ id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Post not Found. id="+ id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) // increase read speed
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                // convert Posts to responseDto
                .map(PostsListResponseDto::new) // == .map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts post = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Post not Found. id="+id));
        postsRepository.delete(post);
    }
}

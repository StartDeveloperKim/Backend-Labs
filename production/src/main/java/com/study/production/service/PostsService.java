package com.study.production.service;

import com.study.production.domain.posts.Posts;
import com.study.production.domain.posts.PostsRepository;
import com.study.production.web.dto.PostsListResponseDto;
import com.study.production.web.dto.PostsResponseDto;
import com.study.production.web.dto.PostsSaveRequestDto;
import com.study.production.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = getPosts(id);

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public void delete (Long id) {
        Posts posts = getPosts(id);
        postsRepository.delete(posts);
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts posts = getPosts(id);
        return new PostsResponseDto(posts);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    private Posts getPosts(Long id) {
        return postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
    }
}

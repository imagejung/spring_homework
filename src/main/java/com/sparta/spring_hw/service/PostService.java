package com.sparta.spring_hw.service;

import com.sparta.spring_hw.dto.PostDeleteDto;
import com.sparta.spring_hw.dto.PostRequestDto;
import com.sparta.spring_hw.dto.PostResponseDto;
import com.sparta.spring_hw.entity.Post;
import com.sparta.spring_hw.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 게시글 작성
    @Transactional
    public Post createPost(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        postRepository.save(post);
        return post;
    }

    // 전체 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        List<PostResponseDto> dtoList = new ArrayList<>();
        postRepository.findAllByOrderByModifiedAtDesc().forEach(
                x -> {
                    PostResponseDto dto = new PostResponseDto(x);
                    dtoList.add(dto);
                });
        return dtoList;
    }

    // 선택한 게시글 조회
    @Transactional
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    // 선택한 게시글 수정
    @Transactional
    public Long updatePost(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findFirstByIdAndPw(id, requestDto.getPw()).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        post.update(requestDto);
        return id;
    }

    // 선택한 게시글 삭제
    @Transactional
    public Long deletePost(Long id, PostDeleteDto deleteDto) {
        postRepository.deleteByIdAndPw(id, deleteDto.getPw());
        return 0L;
    }


}

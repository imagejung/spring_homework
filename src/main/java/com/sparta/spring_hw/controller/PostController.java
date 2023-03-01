package com.sparta.spring_hw.controller;


import com.sparta.spring_hw.dto.PostDeleteDto;
import com.sparta.spring_hw.dto.PostRequestDto;
import com.sparta.spring_hw.dto.PostResponseDto;
import com.sparta.spring_hw.entity.Post;
import com.sparta.spring_hw.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @PostMapping("api/post")
    public Post createPost(@RequestBody PostRequestDto requestDto){
        return postService.createPost(requestDto);
    }

    // 전체 게시글 목록 조회
    @GetMapping("api/posts")
    public List<PostResponseDto> showAll(){
        return postService.getPosts();
    }

    // 선택한 게시글 조회
    @GetMapping("api/post/{id}")
    public PostResponseDto showPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // 선택한 게시글 수정
    @PutMapping("api/post/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto){
        return postService.updatePost(id, requestDto);
    }

    // 선택한 게시글 삭제
    @DeleteMapping("api/post/{id}")
    public Long deletePost(@PathVariable Long id, @RequestBody PostDeleteDto deleteDto){
        return postService.deletePost(id, deleteDto);
    }




}

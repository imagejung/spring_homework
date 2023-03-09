package com.sparta.spring_hw.controller;

import com.sparta.spring_hw.dto.MessageResponseDto;
import com.sparta.spring_hw.dto.PostDeleteDto;
import com.sparta.spring_hw.dto.PostRequestDto;
import com.sparta.spring_hw.dto.PostResponseDto;
import com.sparta.spring_hw.entity.Post;
import com.sparta.spring_hw.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;


    // 게시글 작성
    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, HttpServletRequest request){
        return postService.createPost(postRequestDto, request);
    }

    // 전체 게시글 목록 조회
    @GetMapping("/posts")
    public List<PostResponseDto> showAll(){
        return postService.getPosts();
    }

    // 선택한 게시글 조회
    @GetMapping("/post/{id}")
    public PostResponseDto showPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // 선택한 게시글 수정
    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request){
        return postService.updatePost(id, postRequestDto, request);
    }

    // 선택한 게시글 삭제
    @DeleteMapping("/post/{id}")
    public MessageResponseDto deletePost(@PathVariable Long id, HttpServletRequest request){
        return postService.deletePost(id, request);
    }

}

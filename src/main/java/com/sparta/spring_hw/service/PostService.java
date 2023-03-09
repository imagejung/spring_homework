package com.sparta.spring_hw.service;

import com.sparta.spring_hw.dto.MessageResponseDto;
import com.sparta.spring_hw.dto.PostDeleteDto;
import com.sparta.spring_hw.dto.PostRequestDto;
import com.sparta.spring_hw.dto.PostResponseDto;
import com.sparta.spring_hw.entity.Post;
import com.sparta.spring_hw.entity.User;
import com.sparta.spring_hw.jwt.JwtUtil;
import com.sparta.spring_hw.repository.PostRepository;
import com.sparta.spring_hw.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private User getUserInfo(HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        User user = null;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
        }
        return user;
    }

    // 게시글 작성
    @Transactional(readOnly = true)
    public PostResponseDto createPost(PostRequestDto postRequestDto, HttpServletRequest request) {
        User user = getUserInfo(request);
        Post post = new Post(postRequestDto, user);
        postRepository.save(post);
        return new PostResponseDto(post);
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
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, HttpServletRequest request) {
        User user = getUserInfo(request);
        Post post = postRepository.findByIdAndUsername(id, user.getUsername()).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );
        post.update(postRequestDto);
        return new PostResponseDto(post);
    }

    // 선택한 게시글 삭제
    @Transactional
    public MessageResponseDto deletePost(Long id, HttpServletRequest request) {
        User user = getUserInfo(request);
        Post post = postRepository.findByIdAndUsername(id, user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        postRepository.deleteById(id);
        return new MessageResponseDto("게시글을 삭제했습니다.",200);
    }


}

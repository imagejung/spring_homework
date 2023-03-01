package com.sparta.spring_hw.repository;

import com.sparta.spring_hw.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();
    Optional<Post> findFirstByIdAndPw(Long id, String pw);
    void deleteByIdAndPw(Long id, String pw);
}
package com.example.demo.article.domain.repository;

import com.example.demo.article.domain.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByTitle(String title, Pageable pageable);
    Page<Article> findByContents(String contents, Pageable pageable);
}

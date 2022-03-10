package com.example.demo.article.service;

import com.example.demo.article.controller.dto.ArticleResponse;
import com.example.demo.article.controller.dto.UpdateRequest;
import com.example.demo.article.domain.entity.Article;
import com.example.demo.article.domain.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    private ArticleRepository repository;

    @Test
    void save() {

        //given
        String writer = "철수";
        String contents = "게시글 내용";
        String title = "게시글 제목";

        ArticleResponse dto = ArticleResponse.builder().writer(writer).contents(contents).title(title).build();

        //when
        Article savedEntity = repository.save(dto.toEntity());

        //then
        assertThat(savedEntity.getContents()).isEqualTo(contents);
        assertThat(savedEntity.getWriter()).isEqualTo(writer);
        assertThat(savedEntity.getTitle()).isEqualTo(title);

    }

    @Test
    void view(){
        //given
        String writer = "철수";
        String contents = "게시글 내용";
        String title = "게시글 제목";
        ArticleResponse dto = ArticleResponse.builder().writer(writer).contents(contents).title(title).build();

        //when
        Article savedEntity = repository.save(dto.toEntity());
        Optional<Article> findEntity = repository.findById(savedEntity.getSequence());

        //then
        assertThat(findEntity.get()).isNotNull();
        assertThat(findEntity.get().getTitle()).isEqualTo(savedEntity.getTitle());
        assertThat(findEntity.get().getContents()).isEqualTo(savedEntity.getContents());
        assertThat(findEntity.get().getWriter()).isEqualTo(savedEntity.getWriter());
    }

    @Test
    void update(){
        //given
        String writer = "철수";
        String contents = "게시글 내용";
        String title = "게시글 제목";
        ArticleResponse dto = ArticleResponse.builder().writer(writer).contents(contents).title(title).build();

        String reContents = "게시글 내용 수정";
        String reTitle = "게시글 제목 수정정";
        UpdateRequest updateDto = UpdateRequest.builder().title(reTitle).contents(reContents).build();

        //when
        Article savedEntity = repository.save(dto.toEntity());
        Optional<Article> searchedEntity = repository.findById(savedEntity.getSequence());
        searchedEntity.get().update(updateDto);

        Optional<Article> reSearchedEntity = repository.findById(savedEntity.getSequence());
    }
}

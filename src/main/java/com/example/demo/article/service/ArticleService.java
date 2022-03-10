package com.example.demo.article.service;

import lombok.RequiredArgsConstructor;
import com.example.demo.article.controller.dto.SearchRequest;
import com.example.demo.article.controller.dto.UpdateRequest;
import com.example.demo.article.controller.dto.WriteRequest;
import com.example.demo.article.domain.entity.Article;
import com.example.demo.article.domain.repository.ArticleRepository;
import com.example.demo.members.domain.entity.Members;
import com.example.demo.members.domain.repository.MemberRepository;
import com.example.demo.members.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public Article save(WriteRequest dto){
        Members members = memberRepository.findByUsername(dto.getWriter()).orElseThrow(
                () -> {throw new IllegalArgumentException("IllegalArgumentException");}
        );
        Article article = dto.toEntity();
        article.setMembers(members);
        return articleRepository.save(article);
    }

    public Article view(Long sequence) {
        Optional<Article> searchedBoard = articleRepository.findById(sequence);
        if(searchedBoard.isEmpty()) throw new IllegalArgumentException();
        return searchedBoard.get();
    }

    @Transactional
    public Article update(UpdateRequest dto) {
        Optional<Article> searchedBoard = articleRepository.findById(dto.getSequence());
        if(searchedBoard.isEmpty()) throw new IllegalArgumentException();
        Article targetArticle = searchedBoard.get();
        targetArticle.update(dto);
        return targetArticle;
    }

    @Transactional
    public void delete(Long seqeuence) {
        Article searchedArticle = articleRepository.findById(seqeuence).orElseThrow(
                () -> {throw new IllegalArgumentException("IllegalArgumentException");}
        );
        articleRepository.delete(searchedArticle);
    }

    public List<Article> list() {
        return articleRepository.findAll();
    }

    public Page<Article> pagingList(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public Page<Article> pagingListByOption(SearchRequest dto, Pageable pageable) {
        if(dto.getSearchValue() == null || "".equals(dto.getSearchValue())) return articleRepository.findAll(pageable);

        if("title".equals(dto.getSearchOption())) return articleRepository.findByTitle(dto.getSearchValue(), pageable);
        else return articleRepository.findByContents(dto.getSearchValue(), pageable);
    }
}

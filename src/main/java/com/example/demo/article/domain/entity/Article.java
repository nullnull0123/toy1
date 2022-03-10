package com.example.demo.article.domain.entity;

import com.example.demo.article.controller.dto.ArticleResponse;
import com.example.demo.article.controller.dto.UpdateRequest;
import com.example.demo.common.domain.BaseTimeEntity;
import com.example.demo.members.domain.entity.Members;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Article extends BaseTimeEntity {

    @GeneratedValue
    @Column(name = "article_sequence")
    @Id
    private Long sequence;

    private String title;

    private String contents;

    private String writer;

    @ManyToOne
    @JoinColumn(name = "user_sequence")
    private Members members;

    public void setMembers(Members members) {
        this.members = members;
    }

    @Builder
    private Article(String title, String contents, String writer) {
        this.contents = contents;
        this.title = title;
        this.writer = writer;
    }

    @Transactional
    public void update(UpdateRequest dto) {
        this.title = dto.getTitle();
        this.contents = dto.getContents();
    }

    public ArticleResponse toDto(){
        return ArticleResponse.builder().title(title).contents(contents).writer(writer).build();
    }
}

package com.example.demo.article.controller.dto;

import lombok.*;
import com.example.demo.article.domain.entity.Article;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequest {

    private Long sequence;

    private String title;

    private String contents;

    public Article toEntity(){
        return Article.builder().title(title).contents(contents).build();
    }
}

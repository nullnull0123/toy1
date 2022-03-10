package com.example.demo.article.controller.dto;

import lombok.*;
import com.example.demo.article.domain.entity.Article;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WriteRequest {

    private String title;

    private String contents;

    private String writer;

    public Article toEntity(){
        return Article.builder().title(title).contents(contents).writer(writer).build();
    }
}

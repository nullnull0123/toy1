package com.example.demo.article.controller.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchRequest {
    public String searchOption;
    public String searchValue;
}

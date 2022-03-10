package com.example.demo.article.controller.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PagingRequest {
    public int pageNumber;
    public int pageSize;
}

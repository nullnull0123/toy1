package com.example.demo.article.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.demo.article.controller.dto.PagingRequest;
import com.example.demo.article.controller.dto.SearchRequest;
import com.example.demo.article.controller.dto.UpdateRequest;
import com.example.demo.article.controller.dto.WriteRequest;
import com.example.demo.article.domain.entity.Article;
import com.example.demo.article.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/article")
@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("")
    public String pagingList(Model model, PagingRequest pagingRequest, SearchRequest searchRequest) {
        int pageNumber = pagingRequest.getPageNumber();

        setPagingDto(pagingRequest, pageNumber);

        PageRequest pageRequest = PageRequest.of(pagingRequest.getPageNumber(), pagingRequest.getPageSize());
        Page<Article> articles = articleService.pagingListByOption(searchRequest, pageRequest);

        Long total = articles.getTotalElements();
        Long size = total % pagingRequest.getPageSize() == 0 ? total / pagingRequest.getPageSize() : total / pagingRequest.getPageSize() + 1;

        model.addAttribute("list", articles.getContent());
        model.addAttribute("total", total);
        model.addAttribute("size", size);
        return "article/list";
    }

    private void setPagingDto(PagingRequest pagingRequest, int pageNumber) {
        if(pageNumber ==0) pagingRequest.setPageNumber(0);
        else pagingRequest.setPageNumber(pageNumber -1);
        if(pagingRequest.getPageSize()==0) pagingRequest.setPageSize(10);
    }

    @GetMapping("/write")
    public String write(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("writer", authentication.getName());
        return "article/writeForm";
    }

    @PostMapping("/write")
    public String add(Model model, WriteRequest dto) {
        Article savedArticle = articleService.save(dto);
        return "redirect:/article/"+ savedArticle.getSequence();
    }

    @GetMapping("/{sequence}")
    public String view(@PathVariable Long sequence,Model model) {
        model.addAttribute("article", articleService.view(sequence));
        return "article/viewForm";
    }

    @GetMapping("/{sequence}/edit")
    public String edit(@PathVariable Long sequence, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        checkWriter(sequence);

        model.addAttribute("article", articleService.view(sequence));
        model.addAttribute("writer", authentication.getName());
        return "article/editForm";
    }

    @PutMapping("/{sequence}/edit")
    public String editSave(@PathVariable Long sequence, Model model, UpdateRequest dto){
        articleService.update(dto);
        return "redirect:/article/{sequence}";
    }

    @DeleteMapping("/{sequence}/delete")
    public String delete(@PathVariable Long sequence, Model model) {
        checkWriter(sequence);
        articleService.delete(sequence);
        return "redirect:/article";
    }

    private void checkWriter(Long sequence) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Article article = articleService.view(sequence);
        if(authentication.getName() != article.getWriter()){
            throw new IllegalArgumentException("IllegalArgumentException");
        }
    }
}

package com.study.production.controller;

import com.study.production.dto.ArticleRequest;
import com.study.production.dto.ArticleResponse;
import com.study.production.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public String showArticlesView() {
        return "/article";
    }

    @GetMapping("/newarticle")
    public String showEditView() {
        return "/edit";
    }

    @GetMapping("/{id}")
    public String showArticleDetail(@PathVariable(name = "id", required = true) Long id,
                                    Model model) {
        ArticleResponse article = articleService.getArticle(id);
        model.addAttribute("article", article);

        return "/detail";
    }

    @PostMapping
    public String addArticle(@ModelAttribute ArticleRequest request) {
        Long id = articleService.addArticle(request);
        return "redirect:/article/" + id;

    }
}

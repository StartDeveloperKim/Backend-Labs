package com.study.production.service;

import com.study.production.domain.Article;
import com.study.production.dto.ArticleRequest;
import com.study.production.dto.ArticleResponse;
import com.study.production.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Long addArticle(ArticleRequest request) {
        Article article = Article.builder()
                .title(request.title())
                .content(request.content())
                .build();
        return articleRepository.save(article).getId();
    }

    public ArticleResponse getArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new ArticleResponse(article.getTitle(), article.getContent(), article.getCreateAt());
    }
}

package com.study.todo.auth.jwt;

import com.study.todo.dto.UserInfo;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Token token = parseToken(request);
            log.info("JWT Filter running...");

            if (token != null && tokenProvider.validateToken(token.getAccessToken())) {
                log.info("Token Info : {}", token.toString());
                UserInfo userinfo = tokenProvider.getTokenInfo(token.getAccessToken());

                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userinfo, null, AuthorityUtils.NO_AUTHORITIES);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
            }
        } catch (ExpiredJwtException e) {
            // 엑세스토큰 만료시 리프레쉬 토큰 검사 후 엑세스 토큰 재생성
        } catch (Exception e) {
            e.getMessage();
        }

        filterChain.doFilter(request, response);
    }

    private Token parseToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        String refreshToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            if (StringUtils.hasText(refreshToken) && refreshToken.startsWith("Refresh ")) {
                return new Token(bearerToken.substring(7), refreshToken.substring(8));
            }
        }
        return null;
    }
}

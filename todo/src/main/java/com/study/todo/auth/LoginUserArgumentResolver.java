package com.study.todo.auth;

import com.study.todo.dto.UserInfo;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserInfoClass = UserInfo.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserInfoClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        /*나중에 스프링 시큐리티의 SecurityContext에서 JWT 검증 후 넣은 UserInfo 인스턴스를 가져와서 반환하자
        * 지금은 단순히 userInfo 인스턴스를 반환한다.*/
        return new UserInfo("kkk");
    }
}

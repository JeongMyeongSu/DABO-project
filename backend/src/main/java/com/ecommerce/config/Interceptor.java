package com.ecommerce.config;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
@Component
public class Interceptor extends HandlerInterceptorAdapter {
    Logger logger = LoggerFactory.getLogger("io.ojw.mall.interceptor.JwtInterceptor");
    private static final String TOKEN = "accessToken";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        final String token = request.getHeader(TOKEN);

        logger.debug("JwtInterceptor > preHandle > token: " + token);

        if (StringUtils.equals(request.getMethod(), "OPTIONS")) {
            logger.debug("if request options method is options, return true");

            return true;
        }
        return true;

//        if(token != null && jwtService.checkToken(token)){
//            return true;
//        }else{
//            throw new UnauthorizedException();
//        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("response status: {}", response.getStatus());
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
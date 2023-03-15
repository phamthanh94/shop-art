package com.smart.config;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

@Component
public class LocaleConfigurerFilter extends OncePerRequestFilter {

    private LocaleResolver localeResolver;

    protected void initFilterBean() throws ServletException {
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        Map resolvers = wac.getBeansOfType(LocaleResolver.class);
        if (resolvers.size()==1) {
            localeResolver = (LocaleResolver) resolvers.values().iterator().next();
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Locale locale;
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        Map resolvers = wac.getBeansOfType(LocaleResolver.class);
        if (resolvers.size()==1) {
            localeResolver = (LocaleResolver) resolvers.values().iterator().next();
        }
        if (localeResolver!=null) {
            locale = localeResolver.resolveLocale(request);
            LocaleContextHolder.setLocale(locale);
        }
        locale = RequestContextUtils.getLocale(request);
        LocaleContextHolder.setLocale(locale);
        filterChain.doFilter(request, response);
    }
}

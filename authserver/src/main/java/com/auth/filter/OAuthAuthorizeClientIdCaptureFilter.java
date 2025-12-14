package com.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * This is used to capture the client id and other request params from /authorize request
 *
 */
@Component
public class OAuthAuthorizeClientIdCaptureFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {


        if ("/oauth2/authorize".equals(request.getRequestURI())) {

            Map<String, String[]> params = new HashMap<>(request.getParameterMap());

            request.getSession().setAttribute("AUTH_PARAMS", params);
        }




        filterChain.doFilter(request, response);
    }
}

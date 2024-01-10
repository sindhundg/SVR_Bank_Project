package com.authenticate.AuthenticationService.filter;
import com.authenticate.AuthenticationService.model.Customer;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Filter extends GenericFilter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse=(HttpServletResponse)  servletResponse;
        String token=httpServletRequest.getHeader("Authorization");
        if(token==null || !token.startsWith("Bearer")){
            throw  new ServletException();
        }
        else{
            String jwtTok=token.substring(7);
            String email=Jwts.parser().setSigningKey("secretkey").parseClaimsJws(jwtTok).getBody().getSubject();
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }

    }
}

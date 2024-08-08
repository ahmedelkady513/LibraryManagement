package cc.maids.librarymanagement.config.security.filter;

import cc.maids.librarymanagement.config.security.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private final JwtService jwtService;
    private final HandlerExceptionResolver resolver;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService, HandlerExceptionResolver resolver) {
        super(authenticationManager);
        this.jwtService = jwtService;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            Authentication authentication = getAuthentication(authorizationHeader);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (JwtException e) {
            resolver.resolveException(request, response, null, e);
        }
    }

    private Authentication getAuthentication(String authorizationHeader) throws JwtException {

        String token = authorizationHeader.replace("Bearer ", "");
        jwtService.validateToken(token);
        return new UsernamePasswordAuthenticationToken(jwtService.extractSubject(token), null, new ArrayList<>());


    }

}

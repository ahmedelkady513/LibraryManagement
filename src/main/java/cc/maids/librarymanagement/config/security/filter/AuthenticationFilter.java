package cc.maids.librarymanagement.config.security.filter;

import cc.maids.librarymanagement.config.security.service.JwtService;
import cc.maids.librarymanagement.user.entity.AdminUser;
import cc.maids.librarymanagement.user.request.LoginRequest;
import cc.maids.librarymanagement.user.response.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.ArrayList;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtService jwtService;
    private final HandlerExceptionResolver resolver;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    public AuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService, HandlerExceptionResolver resolver) {
        super(authenticationManager);
        this.jwtService = jwtService;
        this.resolver = resolver;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            LoginRequest loginRequest = OBJECT_MAPPER.readValue(request.getInputStream(), LoginRequest.class);
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                    loginRequest.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        AdminUser adminUser = (AdminUser) authResult.getPrincipal();
        String token = jwtService.generateToken(adminUser.getUsername());

        LoginResponse loginResponseDto = new LoginResponse();
        loginResponseDto.setUsername(adminUser.getUsername());
        loginResponseDto.setToken(token);


        String json = OBJECT_MAPPER.writeValueAsString(loginResponseDto);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        resolver.resolveException(request, response, null, failed);
    }


}

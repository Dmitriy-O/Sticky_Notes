package org.byovsiannikov.sticky_notes.jwt2;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class GetCSRFTokenFilter extends OncePerRequestFilter {

    //Matcher for path's
    private RequestMatcher requestMatcher = new AntPathRequestMatcher("/login", HttpMethod.GET.name());
    private CsrfTokenRepository csrfTokenRepository = new CookieCsrfTokenRepository();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (this.requestMatcher.matches(request)) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            this.objectMapper.writeValue(response.getWriter(),this.csrfTokenRepository.loadDeferredToken(request,response).get());
        }
        filterChain.doFilter(request,response);

    }
}

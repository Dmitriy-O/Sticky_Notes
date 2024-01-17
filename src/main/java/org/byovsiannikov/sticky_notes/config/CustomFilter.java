package org.byovsiannikov.sticky_notes.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.byovsiannikov.sticky_notes.jwt.DefaultAccessTokenFactory;
import org.byovsiannikov.sticky_notes.jwt.DefaultRefreshTokenFactory;
import org.byovsiannikov.sticky_notes.model.Token;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.function.Function;

public class CustomFilter extends OncePerRequestFilter {
    private RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/v1/", HttpMethod.POST.name());
    private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();
    private Function<Authentication, Token> refreshTokenFactory = new DefaultRefreshTokenFactory();
    private Function<Token, Token> accsessTokenFactory = new DefaultAccessTokenFactory();

    private Function<Token, String> refreshtokenSerializer = Object::toString;
    private Function<Token, String> accsesstokenSerializer = Object::toString;

    private ObjectMapper mapper = new ObjectMapper();

    public void setRequestMatcher(RequestMatcher requestMatcher) {
        this.requestMatcher = requestMatcher;
    }

    public void setSecurityContextRepository(SecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
    }

    public void setRefreshTokenFactory(Function<Authentication, Token> refreshTokenFactory) {
        this.refreshTokenFactory = refreshTokenFactory;
    }

    public void setAccsessTokenFactory(Function<Token, Token> accsessTokenFactory) {
        this.accsessTokenFactory = accsessTokenFactory;
    }

    public void setRefreshtokenSerializer(Function<Token, String> refreshtokenSerializer) {
        this.refreshtokenSerializer = refreshtokenSerializer;
    }

    public void setAccsesstokenSerializer(Function<Token, String> accsesstokenSerializer) {
        this.accsesstokenSerializer = accsesstokenSerializer;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (requestMatcher.matches(request)) {
            if (securityContextRepository.containsContext(request)){
                securityContextRepository.loadDeferredContext(request);
            }

        }
        filterChain.doFilter(request, response);

    }
}

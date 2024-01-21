package org.byovsiannikov.sticky_notes.jwt.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.byovsiannikov.sticky_notes.jwt.create.DefaultAccessTokenFactory;
import org.byovsiannikov.sticky_notes.jwt.create.DefaultRefreshTokenFactory;
import org.byovsiannikov.sticky_notes.model.Token;
import org.byovsiannikov.sticky_notes.model.TokenReturn;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.function.Function;

@Setter
public class RequestJWTTokenFilter extends OncePerRequestFilter {
    //Match post request
    private RequestMatcher requestMatcher = new AntPathRequestMatcher("/jwt/tokens", HttpMethod.POST.name());

    //Get context without session an per one request

    private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();


    private Function<Authentication, Token> refreshTokenFactory = new DefaultRefreshTokenFactory();

    private Function<Token, Token> accessTokenFactory = new DefaultAccessTokenFactory();

    //    meaning it'll call the toString() method on the token object.
    private Function<Token, String> refreshTokenStringSerializer = Object::toString;

    private Function<Token, String> accessTokenStringSerializer = Object::toString;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (this.requestMatcher.matches(request)) {
            if (this.securityContextRepository.containsContext(request)) {
                //Get context(data of authenticated user)
                SecurityContext context = this.securityContextRepository.loadDeferredContext(request).get();
                //instance of token  ?? PreAuthenticatedAuthenticationToken object can be treated as a user for role-based authorization
                if (context != null && !(context.getAuthentication() instanceof PreAuthenticatedAuthenticationToken)) {
                    Token refreshToken = this.refreshTokenFactory.apply(context.getAuthentication());
                    Token accessToken = this.accessTokenFactory.apply(refreshToken);
                    //creating response
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    this.objectMapper.writeValue(response.getWriter(),
                            new TokenReturn(this.accessTokenStringSerializer.apply(accessToken),
                                    accessToken.expiresAt().toString(),
                                    this.refreshTokenStringSerializer.apply(refreshToken),
                                    refreshToken.expiresAt().toString()));
                    return;
                }
            }

            throw new AccessDeniedException("User must be authenticated");
        }

        filterChain.doFilter(request, response);
    }


}

package org.byovsiannikov.sticky_notes.jwt2.strategies;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.byovsiannikov.sticky_notes.jwt2.factories.TokenCookieFactory;
import org.byovsiannikov.sticky_notes.dto.Token;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.function.Function;


/**
 * How we handle sessions on successful authentication
 */
@Setter
public class TokenCookieAuthenticationStrategyImpl implements SessionAuthenticationStrategy {

    //Authentication - temporal entry card
    private Function<Authentication, Token> tokenCookieFactory = new TokenCookieFactory();
    private Function<Token, String> tokenSerializer = Objects::toString;

    @Override
    public void onAuthentication(Authentication authentication,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws SessionAuthenticationException {
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            Token token = tokenCookieFactory.apply(authentication);
            String serializedToken = this.tokenSerializer.apply(token);
            //Setting __Host- attributes
            Cookie cookie = new Cookie("__Host-auth-token", serializedToken);
            cookie.setPath("/");
            cookie.setDomain(null);
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setMaxAge((int) ChronoUnit.SECONDS.between(Instant.now(), token.expiresAt()));

            response.addCookie(cookie);

        }
    }
}

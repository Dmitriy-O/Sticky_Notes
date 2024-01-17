package org.byovsiannikov.sticky_notes.jwt;

import org.byovsiannikov.sticky_notes.model.Token;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class DefaultRefreshTokenFactory implements Function<Authentication, Token> {

    private Duration accessTokenTimeValidity = Duration.ofDays(1);
    private Instant now = Instant.now();

    public void setAccessTokenTimeValidity(Duration accessTokenTimeValidity) {
        this.accessTokenTimeValidity = accessTokenTimeValidity;
    }

    @Override
    public Token apply(Authentication authentication) {
        List<String> authorities = new LinkedList<>();
        authorities.add("JWT_REFRESH");
        authorities.add("JWT_LOGOUT");
        authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> "GRANT_" + authority)
                .forEach(authorities::add);


        return new Token
                (UUID.randomUUID(),
                        authentication.getName(),
                        authorities,
                        now,
                        now.plus(accessTokenTimeValidity));
    }
}

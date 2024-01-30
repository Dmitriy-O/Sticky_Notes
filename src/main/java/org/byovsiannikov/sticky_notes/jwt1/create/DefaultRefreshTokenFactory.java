package org.byovsiannikov.sticky_notes.jwt1.create;

import lombok.Setter;
import org.byovsiannikov.sticky_notes.dto.Token;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

/**
 * Transforms Authentication request to Token
 */
public class DefaultRefreshTokenFactory implements Function<Authentication, Token> {

    @Setter
    private Duration accessTokenTimeValidity = Duration.ofDays(1);
    private Instant now = Instant.now();

//    public void setAccessTokenTimeValidity(Duration accessTokenTimeValidity) {
//        this.accessTokenTimeValidity = accessTokenTimeValidity;
//    }

    @Override
    public Token apply(Authentication authentication) {
        List<String> authorities = new LinkedList<>();

        //user can have multiple authorities such as admin and super anmin
        authorities.add("JWT_REFRESH");
        authorities.add("JWT_LOGOUT");
        //example
        //USER
        //ADMIN
        authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> "GRANT_" + authority)
                .forEach(authorities::add);


    // UUID - immutable representation of unique identifier
    // We dont use Authentication embeded authorities
        return new Token
                (UUID.randomUUID(),
                        authentication.getName(),
                        authorities,
                        now,
                        now.plus(accessTokenTimeValidity));
    }
}

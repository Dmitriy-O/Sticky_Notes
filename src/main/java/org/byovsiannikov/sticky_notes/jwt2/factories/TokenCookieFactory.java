package org.byovsiannikov.sticky_notes.jwt2.factories;

import org.byovsiannikov.sticky_notes.dto.Token;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Function;

public class TokenCookieFactory implements Function<Authentication, Token> {
    private Duration durationOfTokenLife=Duration.ofDays(1);
    @Override
    public Token apply(Authentication authentication) {
        Instant createdAt = Instant.now();
        return new Token(UUID.randomUUID(),authentication.getName(),
                authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList(), createdAt,createdAt.plus(durationOfTokenLife)  );
    }
}

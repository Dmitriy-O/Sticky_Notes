package org.byovsiannikov.sticky_notes.jwt;

import org.byovsiannikov.sticky_notes.model.Token;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

public class DefaultAccessTokenFactory implements Function<Token, Token> {

    public void setDurationRefreshToken(Duration durationRefreshToken) {
        this.durationRefreshToken = durationRefreshToken;
    }

    private Duration durationRefreshToken = Duration.ofMinutes(5);
    private  Instant now = Instant.now();

    @Override
    public Token apply(Token token) {
        return new Token(
                token.id(),
                token.subject(),
                token.authorities()
                        .stream().filter(auth -> auth.startsWith("GRANT_"))
                        .map(auth -> auth.substring(6))
                        .toList(), now, now.plus(durationRefreshToken)
        );
    }
}



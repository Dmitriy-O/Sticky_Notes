package org.byovsiannikov.sticky_notes.jwt1.create;

import lombok.Setter;
import org.byovsiannikov.sticky_notes.model.Token;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

/**
 * This class receives TokenRefresh and serialize it to new TokenAccess
 */
public class DefaultAccessTokenFactory implements Function<Token, Token> {

//    public void setDurationRefreshToken(Duration durationRefreshToken) {
//        this.durationRefreshToken = durationRefreshToken;
//    }

    @Setter
    private Duration durationRefreshToken = Duration.ofMinutes(5);
    private Instant now = Instant.now();

    @Override
    public Token apply(Token token) {
        return new Token(
                token.id(),
                token.subject(),
                //Replacing granted authorities from RefreshToken to basic authorities
                token.authorities()
                        .stream().filter(auth -> auth.startsWith("GRANT_"))
                        .map(auth -> auth.substring(6))
                        .toList(), now, now.plus(durationRefreshToken)
        );
    }
}



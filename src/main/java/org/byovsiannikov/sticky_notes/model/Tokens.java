package org.byovsiannikov.sticky_notes.model;

public record Tokens(String accessToken, String accessTokenExp,
                     String refreshToken, String refreshTokenExp) {
}

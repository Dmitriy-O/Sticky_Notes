package org.byovsiannikov.sticky_notes.model;

public record TokenReturn(String accessToken, String accessTokenExp,
                          String refreshToken, String refreshTokenExp) {
}

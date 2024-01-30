package org.byovsiannikov.sticky_notes.dto;

public record TokenReturn(String accessToken, String accessTokenExp,
                          String refreshToken, String refreshTokenExp) {
}

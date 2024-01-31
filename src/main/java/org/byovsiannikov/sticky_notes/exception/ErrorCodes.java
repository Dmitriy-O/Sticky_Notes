package org.byovsiannikov.sticky_notes.exception;

import lombok.Getter;

@Getter
public enum ErrorCodes {

    FORBIDDEN(401),
    UNAUTHORIZED(403),
    NOT_FOUND(404);
    private final int code;

    ErrorCodes(int code) {
        this.code = code;
    }
}

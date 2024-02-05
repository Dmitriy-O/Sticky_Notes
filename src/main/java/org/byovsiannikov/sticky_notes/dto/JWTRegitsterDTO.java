package org.byovsiannikov.sticky_notes.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTRegitsterDTO {
    private String userName;
    private String email;
    private String password;
    private String passwordConfirm;
}

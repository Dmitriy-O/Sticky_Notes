package org.byovsiannikov.sticky_notes.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTLoginDTO {
    private String userName;
    private String password;
}

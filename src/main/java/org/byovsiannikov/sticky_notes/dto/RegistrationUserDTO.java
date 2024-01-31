package org.byovsiannikov.sticky_notes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationUserDTO {
    private String name;
    private String password;
    private String confirmPassword;
    private String email;
}

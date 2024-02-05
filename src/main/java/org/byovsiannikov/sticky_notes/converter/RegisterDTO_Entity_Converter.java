package org.byovsiannikov.sticky_notes.converter;

import lombok.RequiredArgsConstructor;
import org.byovsiannikov.sticky_notes.dto.JWTRegitsterDTO;
import org.byovsiannikov.sticky_notes.entitiy.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class RegisterDTO_Entity_Converter implements Function<JWTRegitsterDTO, UserEntity> {
private final PasswordEncoder passwordEncoder;
    @Override
    public UserEntity apply(JWTRegitsterDTO registrationUserDTO) {
        return UserEntity.builder()
                .name(registrationUserDTO.getUserName())
                .email(registrationUserDTO.getEmail())
                .password(passwordEncoder.encode(registrationUserDTO.getPassword()))
                .build();
    }
}

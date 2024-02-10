package org.byovsiannikov.sticky_notes.converter;

import org.byovsiannikov.sticky_notes.dto.AuthorDTO;
import org.byovsiannikov.sticky_notes.dto.Positions;
import org.byovsiannikov.sticky_notes.entitiy.AuthorEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component

public class AuthorDTO_ENTITY_Converter implements Function<AuthorDTO, AuthorEntity> {
    @Override
    public AuthorEntity apply(AuthorDTO authorDTO) {
        return AuthorEntity.builder()
                .name(authorDTO.getName())
                .surname(authorDTO.getSurname())
                .position(authorDTO.getPosition().name()) //returns value as a string
                .build();
    }
    public AuthorDTO reverseConverter(AuthorEntity authorEntity) {
        return AuthorDTO.builder()
                .name(authorEntity.getName())
                .surname(authorEntity.getSurname())
                .position(Positions.valueOf(authorEntity.getPosition())) //returns value as a Enum
                .build();
    }
}

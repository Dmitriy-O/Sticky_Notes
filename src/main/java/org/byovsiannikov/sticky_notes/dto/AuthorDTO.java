package org.byovsiannikov.sticky_notes.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorDTO {
    private String name;
    private String surname;
    private Positions position;
}

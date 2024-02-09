package org.byovsiannikov.sticky_notes.entitiy;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AuthorEntity {
    private String name;
    private String surname;
    private Positions position;
}

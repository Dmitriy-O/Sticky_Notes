package org.byovsiannikov.sticky_notes.entitiy;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.byovsiannikov.sticky_notes.dto.Positions;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AuthorEntity {
    @Column(name = "author_name")
    private String name;
    @Column(name = "author_surname")
    private String surname;
    @Column(name = "author_role")
    private String position;
}

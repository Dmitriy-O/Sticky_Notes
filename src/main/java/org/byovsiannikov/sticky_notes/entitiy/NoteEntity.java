package org.byovsiannikov.sticky_notes.entitiy;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private AuthorEntity author;
    private String description;
    private BigInteger dateIssue;
    private BigInteger dateUpdated;

}

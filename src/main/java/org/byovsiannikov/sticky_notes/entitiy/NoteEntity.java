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
@Table(name = "note_entity")
public class NoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Embedded
    private AuthorEntity author;
    private String description;
    @Column(name = "date_issue")
    private BigInteger dateIssue;
    @Column(name = "date_update")
    private BigInteger dateUpdated;
    private Boolean isActive;


}

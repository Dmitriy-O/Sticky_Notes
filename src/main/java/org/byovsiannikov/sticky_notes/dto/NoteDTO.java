package org.byovsiannikov.sticky_notes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.byovsiannikov.sticky_notes.entitiy.AuthorEntity;

import java.math.BigInteger;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
    private AuthorEntity author;
    private String description;
    private BigInteger dateIssue;
    private BigInteger dateUpdated;
}

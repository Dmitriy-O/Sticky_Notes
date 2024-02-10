package org.byovsiannikov.sticky_notes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.byovsiannikov.sticky_notes.entitiy.AuthorEntity;

import java.math.BigInteger;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
    private String title;
    private AuthorDTO author;
    private String description;
    private BigInteger dateIssue;
    private BigInteger dateUpdated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteDTO noteDTO = (NoteDTO) o;
        return Objects.equals(title, noteDTO.title) && Objects.equals(author, noteDTO.author) && Objects.equals(description, noteDTO.description) && Objects.equals(dateIssue, noteDTO.dateIssue) && Objects.equals(dateUpdated, noteDTO.dateUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, description, dateIssue, dateUpdated);
    }
}

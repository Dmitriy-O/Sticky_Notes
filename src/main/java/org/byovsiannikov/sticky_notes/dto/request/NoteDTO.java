package org.byovsiannikov.sticky_notes.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.byovsiannikov.sticky_notes.dto.AuthorDTO;
import org.byovsiannikov.sticky_notes.entitiy.AuthorEntity;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Data
@Builder
public class NoteDTO {
    @NotBlank
    private String title;
    private AuthorDTO author;
    private String description;
    private LocalDateTime dateIssue;
    private LocalDateTime dateUpdated;

}

package org.byovsiannikov.sticky_notes.converter;

import lombok.RequiredArgsConstructor;
import org.byovsiannikov.sticky_notes.dto.AuthorDTO;
import org.byovsiannikov.sticky_notes.dto.NoteDTO;
import org.byovsiannikov.sticky_notes.entitiy.AuthorEntity;
import org.byovsiannikov.sticky_notes.entitiy.NoteEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class NoteDTO_Entity_Converter implements Function<NoteDTO, NoteEntity> {

    private final AuthorDTO_ENTITY_Converter authorDTOEntityConverter;
    private final long issueTime = Date.from(Instant.now()).getTime();

    @Override
    public NoteEntity apply(NoteDTO noteDTO) {
        return NoteEntity.builder()
                .title(noteDTO.getTitle())
                .author(authorDTOEntityConverter.apply(noteDTO.getAuthor()))
                .description(noteDTO.getDescription())
                .dateIssue(BigInteger.valueOf(issueTime))
                .dateUpdated(BigInteger.valueOf(issueTime))
                .isActive(true)
                .build();
    }
    public NoteDTO reverseConverter(NoteEntity noteEntity) {
        return NoteDTO.builder()
                .title(noteEntity.getTitle())
                .author(authorDTOEntityConverter.reverseConverter(noteEntity.getAuthor()))
                .description(noteEntity.getDescription())
                .dateIssue(noteEntity.getDateIssue())
                .dateUpdated(noteEntity.getDateUpdated())
                .build();
    }
    public List<NoteDTO> listReverseConverter(List<NoteEntity> noteEntity) {
        return noteEntity
                .stream()
                .map(el->NoteDTO.builder()
                        .title(el.getTitle())
                        .author(authorDTOEntityConverter.reverseConverter(el.getAuthor()))
                        .description(el.getDescription())
                        .dateIssue(el.getDateIssue())
                        .dateUpdated(el.getDateUpdated()).build())
                .toList();
    }
}

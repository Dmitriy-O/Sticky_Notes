package org.byovsiannikov.sticky_notes.converter;

import org.byovsiannikov.sticky_notes.dto.NoteDTO;
import org.byovsiannikov.sticky_notes.entitiy.NoteEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class NoteDTO_Entity_Converter implements Function<NoteDTO, NoteEntity> {

    private final long issueTime = Date.from(Instant.now()).getTime();

    @Override
    public NoteEntity apply(NoteDTO noteDTO) {
        return NoteEntity.builder()
                .author(noteDTO.getAuthor())
                .description(noteDTO.getDescription())
                .dateIssue(BigInteger.valueOf(issueTime))
                .dateUpdated(BigInteger.valueOf(issueTime))
                .build();
    }
    public NoteDTO reverseConverter(NoteEntity noteEntity) {
        return NoteDTO.builder()
                .author(noteEntity.getAuthor())
                .description(noteEntity.getDescription())
                .dateIssue(noteEntity.getDateIssue())
                .dateUpdated(noteEntity.getDateUpdated())
                .build();
    }
    public List<NoteDTO> listReverseConverter(List<NoteEntity> noteEntity) {
        return noteEntity
                .stream()
                .map(el->NoteDTO.builder()
                        .author(el.getAuthor())
                        .description(el.getDescription())
                        .dateIssue(el.getDateIssue())
                        .dateUpdated(el.getDateUpdated()).build())
                .toList();
    }
}

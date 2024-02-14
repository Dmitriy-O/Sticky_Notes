package org.byovsiannikov.sticky_notes.converter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.byovsiannikov.sticky_notes.dto.NoteDTO;
import org.byovsiannikov.sticky_notes.entitiy.NoteEntity;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class NoteDTO_Entity_Converter implements Function<NoteDTO, NoteEntity> {

    private final AuthorDTO_ENTITY_Converter authorDTOEntityConverter;
    private final long issueTime = Instant.now().getEpochSecond();

    public NoteEntity converterForUpdate(NoteDTO noteDTO) {
        return NoteEntity.builder()
                .title(noteDTO.getTitle())
                .description(noteDTO.getDescription())
                .build();
    }

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
                .dateIssue(LocalDateTime.ofInstant(Instant.ofEpochSecond(noteEntity.getDateIssue().longValue()), ZoneId.systemDefault()))
                .dateUpdated(LocalDateTime.ofInstant(Instant.ofEpochSecond(noteEntity.getDateIssue().longValue()), ZoneId.systemDefault()))
                .build();
    }

    public List<NoteDTO> listReverseConverter(List<NoteEntity> noteEntity) {
        return noteEntity
                .stream()
                .map(el -> NoteDTO.builder()
                        .title(el.getTitle())
                        .author(authorDTOEntityConverter.reverseConverter(el.getAuthor()))
                        .description(el.getDescription())
                        .dateIssue(LocalDateTime.ofInstant(Instant.ofEpochSecond(el.getDateIssue().longValue()), ZoneId.of("UTC")))
                        .dateUpdated(LocalDateTime.ofInstant(Instant.ofEpochSecond(el.getDateIssue().longValue()), ZoneId.systemDefault()))
                        .build())
                .toList();

    }
}

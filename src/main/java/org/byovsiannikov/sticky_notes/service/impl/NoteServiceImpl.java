package org.byovsiannikov.sticky_notes.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.byovsiannikov.sticky_notes.converter.NoteDTO_Entity_Converter;
import org.byovsiannikov.sticky_notes.dto.request.NoteDTO;
import org.byovsiannikov.sticky_notes.dto.response.PageResponse;
import org.byovsiannikov.sticky_notes.entitiy.NoteEntity;
import org.byovsiannikov.sticky_notes.repository.NoteRepository;
import org.byovsiannikov.sticky_notes.service.NoteService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final NoteDTO_Entity_Converter noteDTOEntityConverter;

    @Override
    public NoteDTO postNote(NoteDTO note) {
        if (!noteRepository.existsByTitle(note.getTitle())) {
            NoteEntity noteEntity = noteDTOEntityConverter.apply(note);
            noteEntity.setDateIssue(BigInteger.valueOf(Instant.now().getEpochSecond()));
            noteEntity.setDateUpdated(BigInteger.valueOf(Instant.now().getEpochSecond()));
            noteEntity.setIsActive(true);
            noteRepository.save(noteEntity);
            return noteDTOEntityConverter.reverseConverter(noteEntity);
        }
        return null;
    }

    @Override
    public PageResponse getAllNotes(Integer pageNo, Integer pageSize) {
        Pageable pageable= PageRequest.of(pageNo,pageSize);
        Page<NoteEntity> noteEntityPage=noteRepository.findAllByIsActiveTrue(pageable);
        List<NoteEntity> noteEntityList=noteEntityPage.getContent();
        return PageResponse.builder()
                .content(noteEntityList.stream().map(noteDTOEntityConverter::reverseConverter).toList())
                .pageNo(noteEntityPage.getNumber())
                .pageSize(noteEntityPage.getSize())
                .totalPages(noteEntityPage.getTotalPages())
                .totalElements(noteEntityPage.getTotalElements())
                .last(noteEntityPage.isLast())
                .build();
//        return noteEntityPage
//                .map(el->noteDTOEntityConverter.listReverseConverter())
    }

    @Override
    public NoteDTO getNoteById(Long id) {
        if (noteRepository.findByIdAndIsActiveIsFalse(id).isEmpty()) {
            log.error("User with {} id not found ", id);
            return null;
        }
        return noteDTOEntityConverter.reverseConverter(noteRepository.findById(id).get());
    }

    //todo logging
    @Override
    public NoteDTO updateNoteById(Long id, NoteDTO valuesForUpdate) {
        if (noteRepository.findByIdAndIsActiveIsFalse(id).isEmpty()) {
            log.error("User with {} id not found ", id);
            return null;
        }
        NoteEntity updatedNote = noteRepository.findById(id).get();
        BeanUtils.copyProperties(valuesForUpdate, updatedNote, "id", "author", "dateIssue", "isActive");
        updatedNote.setDateUpdated(BigInteger.valueOf(Instant.now().getEpochSecond()));
        noteRepository.save(updatedNote);
        return noteDTOEntityConverter.reverseConverter(updatedNote);
    }

    @Override
    public String deleteNoteById(Long id) {
        if (noteRepository.findByIdAndIsActiveIsFalse(id).isEmpty()) {
            log.error("User with {} id not found or not active ", id);
            return null;
        }
        NoteEntity noteEntity = noteRepository.findById(id).get();

        noteEntity.setIsActive(false);
        noteRepository.save(noteEntity);
        return "Entity was deleted";
    }
}

package org.byovsiannikov.sticky_notes.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.byovsiannikov.sticky_notes.converter.NoteDTO_Entity_Converter;
import org.byovsiannikov.sticky_notes.dto.NoteDTO;
import org.byovsiannikov.sticky_notes.entitiy.NoteEntity;
import org.byovsiannikov.sticky_notes.repository.NoteRepository;
import org.byovsiannikov.sticky_notes.service.NoteService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final NoteDTO_Entity_Converter noteDTOEntityConverter;

    @Override
    public NoteDTO postNote(NoteEntity note) {
        if (noteRepository.findByTitle(note.getTitle()).isEmpty()) {
            noteRepository.save(note);
            return noteDTOEntityConverter.reverseConverter(note);
        }
        return null;
    }

    @Override
    public List<NoteDTO> getAllNotes() {
        return noteRepository.findAllByIsActiveFalse()
                .stream()
                .map(noteDTOEntityConverter::reverseConverter)
                .toList();
    }

    @Override
    public NoteDTO getNoteById(Long id) {
        if (noteRepository.findByIdAndIsActiveIsFalse(id).isEmpty()) {
            log.error("User with {} id not found ", id);
            return null;
        }
        return noteRepository.findById(id).get();
    }

    //todo logging
    @Override
    public NoteDTO updateNoteById(Long id, NoteDTO valuesForUpdate) {
        if (noteRepository.findByIdAndIsActiveIsFalse(id).isEmpty()) {
            log.error("User with {} id not found ", id);
            return null;
        }
        valuesForUpdate.setDateUpdated(BigInteger.valueOf(new Date().getTime()));
        NoteEntity updatedNote = noteRepository.findById(id).get();
        BeanUtils.copyProperties(valuesForUpdate, updatedNote, "id", "author", "dateIssue", "isActive");
        noteRepository.save(updatedNote);
        return updatedNote;
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

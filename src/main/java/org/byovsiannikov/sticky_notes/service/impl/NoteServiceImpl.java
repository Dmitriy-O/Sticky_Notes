package org.byovsiannikov.sticky_notes.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.byovsiannikov.sticky_notes.entitiy.NoteEntity;
import org.byovsiannikov.sticky_notes.repository.NoteRepository;
import org.byovsiannikov.sticky_notes.service.NoteService;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public NoteEntity postNote(NoteEntity note) {
        try {
            if (noteRepository.findByTitle(note.getTitle()).isEmpty()) {
                return noteRepository.save(note);
            }
        } catch (InvalidDataAccessResourceUsageException e) {
            log.info("Can't find the note {}",note.getTitle(),e);
            return  noteRepository.save(note);
        }
        return null;
    }

    @Override
    public List<NoteEntity> getAllNotes() {
        return noteRepository.findAll()
                .stream()
                .filter(NoteEntity::isActive)
                .toList();
    }

    @Override
    public NoteEntity getNoteById(Long id) {
        if (noteRepository.findById(id).isEmpty()) {
            return null;
        }
        if (!noteRepository.findById(id).get().isActive()) {
            return null;
        }
        return noteRepository.findById(id).get();
    }

    @Override
    public NoteEntity updateNoteById(Long id, NoteEntity entityForUpdate) {
        if (noteRepository.findById(id).isEmpty()) {
            return null;
        }
        NoteEntity noteEntityForUpdate = noteRepository.findById(id).get();
        return noteEntityForUpdate.builder()
                .title(entityForUpdate.getTitle())
                .description(entityForUpdate.getDescription())
                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
                .build();
    }

    @Override
    public String deleteNoteById(Long id) {
        NoteEntity noteEntity = noteRepository.getReferenceById(id);
        noteEntity.setActive(false);
        return "Entity was deleted";
    }
}

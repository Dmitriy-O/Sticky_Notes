package org.byovsiannikov.sticky_notes.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.byovsiannikov.sticky_notes.entitiy.NoteEntity;
import org.byovsiannikov.sticky_notes.repository.NoteRepository;
import org.byovsiannikov.sticky_notes.service.NoteService;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.Instant;
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
                .filter(NoteEntity::getIsActive)
                .toList();
    }

    @Override
    public NoteEntity getNoteById(Long id) {
        if (noteRepository.findById(id).isEmpty()) {
            return null;
        }
        if (!noteRepository.findById(id).get().getIsActive()) {
            return null;
        }
        return noteRepository.findById(id).get();
    }
//todo logging
    @Override
    public NoteEntity updateNoteById(Long id, NoteEntity valuesForUpdate) {
        if (noteRepository.findById(id).isEmpty()) {
            return null;
        }
        valuesForUpdate.setDateUpdated(BigInteger.valueOf(new Date().getTime()));
        NoteEntity updatedNote = noteRepository.findById(id).get();
        BeanUtils.copyProperties(valuesForUpdate,updatedNote,"id","author","dateIssue","isActive");
        noteRepository.save(updatedNote);
        return updatedNote;
    }

    @Override
    public String deleteNoteById(Long id) {
        NoteEntity noteEntity = noteRepository.getReferenceById(id);
        noteEntity.setIsActive(false);
        return "Entity was deleted";
    }
}

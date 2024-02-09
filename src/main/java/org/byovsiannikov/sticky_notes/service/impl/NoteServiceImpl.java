package org.byovsiannikov.sticky_notes.service.impl;

import lombok.RequiredArgsConstructor;
import org.byovsiannikov.sticky_notes.dto.NoteDTO;
import org.byovsiannikov.sticky_notes.entitiy.NoteEntity;
import org.byovsiannikov.sticky_notes.repository.NoteRepository;
import org.byovsiannikov.sticky_notes.service.NoteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public NoteEntity postNote(NoteEntity note) {
        return noteRepository.save(note);
    }

    @Override
    public List<NoteEntity> getAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    public NoteEntity getNoteById(Long id) {
        if (noteRepository.findById(id).isEmpty()) {
            return null;
        }
        return noteRepository.findById(id).get();
    }
}

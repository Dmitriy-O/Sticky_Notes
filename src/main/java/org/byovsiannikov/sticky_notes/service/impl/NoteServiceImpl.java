package org.byovsiannikov.sticky_notes.service.impl;

import lombok.RequiredArgsConstructor;
import org.byovsiannikov.sticky_notes.dto.NoteDTO;
import org.byovsiannikov.sticky_notes.repository.NoteRepository;
import org.byovsiannikov.sticky_notes.service.NoteService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

    @Override
    public NoteDTO postNote(NoteDTO note) {
        return null;
    }
}

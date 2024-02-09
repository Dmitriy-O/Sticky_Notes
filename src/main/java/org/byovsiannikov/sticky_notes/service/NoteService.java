package org.byovsiannikov.sticky_notes.service;

import org.byovsiannikov.sticky_notes.dto.NoteDTO;

public interface NoteService {
    NoteDTO postNote(NoteDTO note);
}

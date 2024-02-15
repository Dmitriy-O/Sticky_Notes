package org.byovsiannikov.sticky_notes.service;

import org.byovsiannikov.sticky_notes.dto.NoteDTO;
import org.byovsiannikov.sticky_notes.entitiy.NoteEntity;

import java.util.List;

public interface NoteService {
    NoteDTO postNote(NoteDTO note);
    List<NoteDTO> getAllNotes();
    NoteDTO getNoteById(Long id);
    NoteDTO updateNoteById(Long id,NoteDTO entityForUpdate);
    String deleteNoteById(Long id);

}

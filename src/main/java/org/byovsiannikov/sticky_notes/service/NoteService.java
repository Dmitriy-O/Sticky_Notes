package org.byovsiannikov.sticky_notes.service;

import org.byovsiannikov.sticky_notes.dto.NoteDTO;
import org.byovsiannikov.sticky_notes.entitiy.NoteEntity;

import java.util.List;

public interface NoteService {
    NoteEntity postNote(NoteEntity note);
    List<NoteEntity> getAllNotes();
    NoteEntity getNoteById(Long id);
    NoteEntity updateNoteById(Long id,NoteEntity entityForUpdate);
    String deleteNoteById(Long id);

}

package org.byovsiannikov.sticky_notes.service;

import org.byovsiannikov.sticky_notes.dto.request.NoteDTO;
import org.byovsiannikov.sticky_notes.dto.response.PageResponse;

import java.util.List;

public interface NoteService {
    NoteDTO postNote(NoteDTO note);
    PageResponse getAllNotes(Integer pageNo, Integer pageSize );
    NoteDTO getNoteById(Long id);
    NoteDTO updateNoteById(Long id,NoteDTO entityForUpdate);
    String deleteNoteById(Long id);

}

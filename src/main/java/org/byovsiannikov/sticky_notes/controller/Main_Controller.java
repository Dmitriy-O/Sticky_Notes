package org.byovsiannikov.sticky_notes.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.byovsiannikov.sticky_notes.converter.NoteDTO_Entity_Converter;
import org.byovsiannikov.sticky_notes.dto.NoteDTO;
import org.byovsiannikov.sticky_notes.service.NoteService;
import org.byovsiannikov.sticky_notes.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class Main_Controller {
    private final NoteService noteService;
    private final NoteDTO_Entity_Converter converter;
    @GetMapping("/getInfo")
    public ResponseEntity<?> getInfo()  {
        return ResponseEntity.ok("Hello from info");
    }
    @GetMapping("/getAdminInfo")
    public ResponseEntity<?> getAdminInfo()  {
        return ResponseEntity.ok("Hello from admin info");
    }
    @PostMapping("/postNote")
    public ResponseEntity<?> postNote(@RequestBody NoteDTO noteForPost)  {
        NoteDTO postedNote = converter.reverseConverter(noteService.postNote(converter.apply(noteForPost)));
        return ResponseEntity.ok(postedNote);
    }
    @GetMapping("/getAllNotes")
    public ResponseEntity<?> getAllNotes()  {
        List<NoteDTO> postedNote = converter.listReverseConverter(noteService.getAllNotes());
        return ResponseEntity.ok(postedNote);
    }
    @GetMapping("/getNoteById/{id}")
    public ResponseEntity<?> getNoteById(@PathVariable(name = "id") Long id)  {
        NoteDTO postedNote = converter.reverseConverter(noteService.getNoteById(id));
        return ResponseEntity.ok(postedNote);
    }
    @PutMapping("/updateNoteById/{id}")
    public ResponseEntity<?> updateNote(@PathVariable(name = "id") Long id,@RequestBody NoteDTO noteDTOForUpdate)  {
//        noteService.getNoteById(id);
        NoteDTO postedNote = converter.reverseConverter(noteService.updateNoteById(id,converter.converterForUpdate(noteDTOForUpdate)));
        return ResponseEntity.ok(postedNote);
    }
    @DeleteMapping("/deleteNoteById/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(name = "id") Long id)  {
        String result = noteService.deleteNoteById(id);
        return ResponseEntity.ok(result);
    }
}

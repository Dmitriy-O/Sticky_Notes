package org.byovsiannikov.sticky_notes.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.byovsiannikov.sticky_notes.converter.NoteDTO_Entity_Converter;
import org.byovsiannikov.sticky_notes.dto.request.NoteDTO;
import org.byovsiannikov.sticky_notes.dto.response.PageResponse;
import org.byovsiannikov.sticky_notes.service.NoteService;
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
        NoteDTO postedNote = noteService.postNote(noteForPost);
        return ResponseEntity.ok(postedNote);
    }
    @GetMapping("/getAllNotes")
    public ResponseEntity<?> getAllNotes(
            @RequestParam(value = "pageNumber",required = false,defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize
    )  {
       PageResponse pageResponse =noteService.getAllNotes(pageNumber,pageSize);
        return ResponseEntity.ok(pageResponse);
    }
    @GetMapping("/getNoteById/{id}")
    public ResponseEntity<?> getNoteById(@PathVariable(name = "id") Long id)  {
        NoteDTO postedNote = noteService.getNoteById(id);
        return ResponseEntity.ok(postedNote);
    }
    @PutMapping("/updateNoteById/{id}")
    public ResponseEntity<?> updateNote(@PathVariable(name = "id") Long id,@RequestBody NoteDTO noteDTOForUpdate)  {
//        noteService.getNoteById(id);
        NoteDTO postedNote = noteService.updateNoteById(id,noteDTOForUpdate);
        return ResponseEntity.ok(postedNote);
    }
    @DeleteMapping("/deleteNoteById/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(name = "id") Long id)  {
        String result = noteService.deleteNoteById(id);
        return ResponseEntity.ok(result);
    }
}

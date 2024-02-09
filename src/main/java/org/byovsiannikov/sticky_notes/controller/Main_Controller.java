package org.byovsiannikov.sticky_notes.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.byovsiannikov.sticky_notes.converter.NoteDTO_Entity_Converter;
import org.byovsiannikov.sticky_notes.dto.NoteDTO;
import org.byovsiannikov.sticky_notes.service.NoteService;
import org.byovsiannikov.sticky_notes.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> postNote(NoteDTO noteForPost)  {
        NoteDTO postedNote = converter.reverseConverter(noteService.postNote(converter.apply(noteForPost)));
        return ResponseEntity.ok(postedNote);
    }
}

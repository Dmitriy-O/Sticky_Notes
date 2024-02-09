package org.byovsiannikov.sticky_notes.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @GetMapping("/getInfo")
    public ResponseEntity<?> getInfo()  {
        return ResponseEntity.ok("Hello from info");
    }
    @GetMapping("/getAdminInfo")
    public ResponseEntity<?> getAdminInfo()  {
        return ResponseEntity.ok("Hello from admin info");
    }
    @PostMapping("/postNote")
    public ResponseEntity<?> postNote()  {
        return ResponseEntity
    }
}

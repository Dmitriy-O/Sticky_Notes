package org.byovsiannikov.sticky_notes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class Rest_Controller {

    @PostMapping
    public HttpResponse<?> postNote(){

    }

    @GetMapping
    public HttpResponse<?> getNote(){

    }
}

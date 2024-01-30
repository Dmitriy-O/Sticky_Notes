package org.byovsiannikov.sticky_notes.controller;

import lombok.RequiredArgsConstructor;
import org.byovsiannikov.sticky_notes.jwt1.serializeAnddeserialize.AccsessTokenSerializer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//The Authentication contains:
//
//principal: Identifies the user. When authenticating with a username/password this is often an instance of UserDetails.
//
//credentials: Often a password. In many cases, this is cleared after the user is authenticated, to ensure that it is not leaked.
//
//authorities: The GrantedAuthority instances are high-level permissions the user is granted. Two examples are roles and scopes.
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class Rest_Controller {

    private  final AccsessTokenSerializer serializer;

    @PostMapping("/login")
    public ResponseEntity<?> register(){
        return ResponseEntity.ok().body("Hello from login");
    }

    @PostMapping("/any")

    public ResponseEntity<?> postNote(){
        return ResponseEntity.ok().body("Hello from login");
    }
//
//    @GetMapping
//    public HttpResponse<?> getNote(){
//
//    }
}

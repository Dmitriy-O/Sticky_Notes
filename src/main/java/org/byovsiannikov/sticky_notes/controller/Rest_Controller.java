package org.byovsiannikov.sticky_notes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

//The Authentication contains:
//
//principal: Identifies the user. When authenticating with a username/password this is often an instance of UserDetails.
//
//credentials: Often a password. In many cases, this is cleared after the user is authenticated, to ensure that it is not leaked.
//
//authorities: The GrantedAuthority instances are high-level permissions the user is granted. Two examples are roles and scopes.
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

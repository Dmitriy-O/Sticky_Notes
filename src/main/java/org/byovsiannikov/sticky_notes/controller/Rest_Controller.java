package org.byovsiannikov.sticky_notes.controller;

import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.byovsiannikov.sticky_notes.dto.JWTRequestDTO;
import org.byovsiannikov.sticky_notes.dto.ResponseTokenDTO;
import org.byovsiannikov.sticky_notes.exception.AppErorr;
import org.byovsiannikov.sticky_notes.exception.ErrorCodes;
import org.byovsiannikov.sticky_notes.jwt.TokenCreation;
import org.byovsiannikov.sticky_notes.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

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
@Slf4j
public class Rest_Controller {

    private final TokenCreation tokenCreation;
    private final UserService userService;
    private final AuthenticationManager authenticationManager; //validates authentication operation

    @PostMapping("/login")
    public ResponseEntity<?> register(@RequestBody JWTRequestDTO jwtRequestDTO) throws ParseException, JOSEException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequestDTO.getUserName(),jwtRequestDTO.getPassword()));
        } catch (BadCredentialsException e) {
            log.error(e.getMessage(),e);
            return new ResponseEntity<>(new AppErorr(ErrorCodes.UNAUTHORIZED.getCode(),"Authentification error"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userRetrievedData=userService.loadUserByUsername(jwtRequestDTO.getUserName());

        String serializeToken = tokenCreation.serializeToken(userRetrievedData);

        return ResponseEntity.ok().body(new ResponseTokenDTO(serializeToken));
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

package com.blogging.controllers;

import com.blogging.entities.User;
import com.blogging.payloads.JwtAuthRequest;
import com.blogging.payloads.JwtAuthResponse;
import com.blogging.payloads.UserDto;
import com.blogging.security.JwtTokenHelper;
import com.blogging.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@Valid @RequestBody JwtAuthRequest request) throws AccessDeniedException {

        authenticateRequest(request.getUserName(),request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
        String token = jwtTokenHelper.generateToken(userDetails);

        User user = (User) userDetails;
        JwtAuthResponse response = new JwtAuthResponse(token,mapper.map(user,UserDto.class));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Dk-Access-Token",token);
        return new ResponseEntity<>(response,httpHeaders,HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {

        try {
            UserDto user = userService.registerNewUser(userDto);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private void authenticateRequest(String username, String password) throws AccessDeniedException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username,
                password
        );
        try {
            authenticationManager.authenticate(authenticationToken);
        }catch (Exception ex){
            throw new AccessDeniedException("Oops,Enter valid username and password !");
        }
    }
}

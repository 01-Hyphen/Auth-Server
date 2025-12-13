package com.auth.controller;


import com.auth.dto.UserObjDto;
import com.auth.service.AuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserObjDto userObjDto){
        authService.createUser(userObjDto);
        return new ResponseEntity<>("user has been created!!", HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<String> secured(){
        return new ResponseEntity<>("Welcome", HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<String> admin(){
        return new ResponseEntity<>("Welcome Admin", HttpStatus.OK);
    }

}

package com.auth.controller;

import com.auth.entity.OAuthClientEntity;
import com.auth.service.ClientRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class OAuthClientController {

    private final ClientRegistrationService service;

    @PostMapping("/register")
    public String register(@RequestBody OAuthClientEntity entity) {
        return service.registerClient(entity);
    }

}

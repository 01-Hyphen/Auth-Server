package com.auth.service;

import com.auth.entity.OAuthClientEntity;
import com.auth.repo.OAuthClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientRegistrationService {

    private final OAuthClientRepository oAuthClientRepository;
    private final RegisteredClientRepository registeredClientRepo;
    private final ClientConverter converter;
    public String registerClient(OAuthClientEntity entity) {

        // 1. Store into your own table (for audit)
        oAuthClientRepository.save(entity);

        // 2. Convert to RegisteredClient and store in Spring’s DB table
        RegisteredClient rc = converter.toRegisteredClient(entity);
        registeredClientRepo.save(rc);


        return "Client registered successfully!";
    }
}

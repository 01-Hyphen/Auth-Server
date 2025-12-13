package com.auth.service;

import com.auth.constants.AuthConstant;
import com.auth.entity.OAuthClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.UUID;

@Service
public class ClientConverter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegisteredClient toRegisteredClient(OAuthClientEntity entity){
        RegisteredClient.Builder builder = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId(entity.getClientId())
                .clientName(entity.getClientName());

        // This is for differentiating between PKCE Client as they will not having any secret.
        if("public".equals(entity.getClientType())){
            builder.clientAuthenticationMethod(ClientAuthenticationMethod.NONE);
        }else{
            // if grant type is client credentials.
            builder.clientSecret(passwordEncoder.encode(entity.getClientSecret()));
            builder.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
        }

        Arrays.stream(entity.getGrantTypes().split(","))
                .forEach(gType->{
                    builder.authorizationGrantType(new AuthorizationGrantType(gType));
                });



        Arrays.stream(entity.getAuthMethods().split(","))
                .forEach(method->{
                    builder.clientAuthenticationMethod(
                            new ClientAuthenticationMethod(method)
                    );
                });



        Arrays.stream(entity.getRedirectUris().split(","))
                .forEach(builder::redirectUri);

        Arrays.stream(entity.getScopes().split(","))
                .forEach(builder::scope);

        ClientSettings.Builder clientSettings = ClientSettings.builder();
        if("public".equals(entity.getClientType())){
            clientSettings.requireProofKey(true);
        }
        builder.clientSettings(clientSettings.build());

        TokenSettings.Builder tokenSettings = TokenSettings.builder();
        tokenSettings
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                .reuseRefreshTokens(false)
                .accessTokenTimeToLive(Duration.ofHours(AuthConstant.ACCESS_TOKEN_EXPIRATION_HOURS))
                .refreshTokenTimeToLive(Duration.ofHours(AuthConstant.REFRESH_TOKEN_EXPIRATION_HOURS));

        builder.tokenSettings(tokenSettings.build());

        return builder.build();
    }
}

package com.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "app_auth_clients")
@Getter@Setter@AllArgsConstructor@EqualsAndHashCode
public class OAuthClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientId;
    private String clientSecret;

    private String redirectUris;    // CSV
    private String scopes;          // CSV
    private String grantTypes;      // CSV
    private String authMethods;     // CSV
    private String clientType;
    private String clientName;
}

// src/app/auth/auth.service.ts
import { Injectable } from '@angular/core';
import { generateCodeVerifierAndChallenge } from './pkce-util';

@Injectable({ providedIn: 'root' })
export class AuthService {
  async loginWithPKCE() {
    const { verifier, challenge } = await generateCodeVerifierAndChallenge();
    sessionStorage.setItem('pkce_code_verifier', verifier);
    console.log(verifier, challenge)
    const params = new URLSearchParams({
      response_type: 'code',
      client_id: 'app1-client',
      redirect_uri: 'http://localhost:4200/callback',
      scope: 'openid profile email',
      code_challenge: challenge,
      code_challenge_method: 'S256'
    });

    window.location.href = `http://localhost:8080/oauth2/authorize?${params}`;
  }
}

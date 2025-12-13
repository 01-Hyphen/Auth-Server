// src/app/auth/callback.component.ts
import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, retry } from 'rxjs';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-callback',
  template: `<p>Logging in...</p>`
  // templateUrl: './callback.component.html'
})
export class CallbackComponent implements OnInit {
userId:string="";
token:string="";
  constructor(private http: HttpClient, private router: Router) { }



async ngOnInit() {
  const code = new URLSearchParams(window.location.search).get('code');
  const verifier = sessionStorage.getItem('pkce_code_verifier');

  if (code && verifier) {
    const body = new URLSearchParams({
      grant_type: 'authorization_code',
      code,
      client_id: 'app1-client',
      redirect_uri: 'http://localhost:4200/callback',
      code_verifier: verifier
    });

    const tokens: any = await firstValueFrom(
      this.http.post('http://localhost:8080/oauth2/token', body.toString(), {
        headers: new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' }),
        withCredentials: true
      })
    );

    sessionStorage.setItem('access_token', tokens.access_token);

    const user = await firstValueFrom(this.userinfo());
    this.userId = user.sub;
    console.log(this.userId);
    const res = await firstValueFrom(
      this.http.post('http://localhost:9191/saveRecipeOwner?userNameFromAuthServer='+this.userId,null,{
        headers: new HttpHeaders({ 'Authorization': `Bearer ${sessionStorage.getItem('access_token')}`, 'Content-Type': 'application/json' }),
      })
    );

    console.log(res);
    this.router.navigate(['/dashboard']);
  }
}


  // ngOnInit() {
  //   const code = new URLSearchParams(window.location.search).get('code');
  //   console.log(code)
  //   const verifier = sessionStorage.getItem('pkce_code_verifier');

  //   if (code && verifier) {
  //     const body = new URLSearchParams({
  //       grant_type: 'authorization_code',
  //       code,
  //       client_id: 'app1-client',
  //       redirect_uri: 'http://localhost:4200/callback',
  //       code_verifier: verifier
  //     });

  //     this.http.post('http://localhost:8080/oauth2/token', body.toString(), {
  //       headers: new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' }),
  //       withCredentials: true
  //     }).subscribe((tokens: any) => {
  //       this.token = tokens.access_token;
  //       sessionStorage.setItem('access_token', tokens.access_token);
  //       console.log(sessionStorage.getItem('access_token'))
  //       // this.router.navigate(['/dashboard']);
  //     });

  //   }
  //   this.userinfo().subscribe(user => {
  //     this.userId = user.sub;

  //     this.http.post('http://localhost:9191/saveRecipeOwner', {
  //       userNameFromAuthServer: this.userId
  //     }).subscribe(res => {
  //       console.log(res);
  //       this.router.navigate(['/dashboard']);
  //     });
  //   });
  // };
  

  userinfo():Observable<{sub:string}>{ {
    const token = sessionStorage.getItem('access_token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
   return this.http.get<{sub:string}>('http://localhost:8080/userinfo', {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }),

    });
  }
}
}

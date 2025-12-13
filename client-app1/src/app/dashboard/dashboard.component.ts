// src/app/dashboard/dashboard.component.ts
import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: "./dashboard.component.html"
})
export class DashboardComponent implements OnInit {
  constructor(private http: HttpClient,private router:Router) { }

  ngOnInit(): void {
    const token = sessionStorage.getItem('access_token');
    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });

    this.http.get('http://localhost:8090/account', {
      headers,
      responseType: 'text' as const
    }).subscribe({
      next: data => console.log('✅ Text response:', data),
      error: err => console.error('❌ Error:', err)
    });


  }

  userinfo(){
    const token = sessionStorage.getItem('access_token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    this.http.get('http://localhost:8080/userinfo', {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }),
    
    }).subscribe(user => console.log(user));
  }

  logout() {
    // 1. Clear token from storage
    sessionStorage.removeItem('access_token');

    // 2. Call Spring Boot logout endpoint
    return this.http.post('http://localhost:8080/logout', {}, {
      withCredentials: true // ✅ required to send session cookie
    }).subscribe({
      next: () => {
        // 3. Redirect to login or home page
        this.router.navigateByUrl("/");
      },
      error: err => {
        console.error('Logout failed', err);
      }
    });
  }


}

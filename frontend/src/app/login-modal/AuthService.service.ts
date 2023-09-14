import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { tap } from 'rxjs/operators';

type AuthResponse = {
  token: string;
  expires_at: string;
};

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient, private router: Router) {}

  login(userData: { login: string; password: string }) {
    return (
      this.http
        .post('http://localhost:8080/api/user/login', {
          login: userData.login,
          password: userData.password,
        })
        // .pipe(
        //   tap((response: any) => {
        //     console.log('login response: ', response);
        //     this.setSession(response);
        //     this.router.navigate(['/']);
        //   })
        // );
        .subscribe((res) => {
          console.log('login response: ', res);
          this.setSession(res);
          this.router.navigate(['/']);
        })
    );
  }

  private setSession(response: any) {
    console.log('setSession response: ', response);
    localStorage.setItem('JWT_TOKEN', response.token);
    localStorage.setItem('expires_at', response.expires_at);
  }

  logout() {
    localStorage.removeItem('JWT_TOKEN');
    localStorage.removeItem('expires_at');
  }

  public isLoggedIn() {
    return moment().isBefore(this.getExpiration());
  }

  isLoggedOut() {
    return !this.isLoggedIn();
  }

  getExpiration() {
    const expiration = localStorage.getItem('expires_at');
    if (expiration) {
      const expiresAt = JSON.parse(expiration);
      return moment(expiresAt);
    }

    return null;
  }

  getToken() {
    return localStorage.getItem('JWT_TOKEN');
  }
}

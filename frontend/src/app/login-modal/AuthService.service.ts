import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ChangeDetectorRef, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { Subject } from 'rxjs';
import { tap } from 'rxjs/operators';
declare var angular: any;

type AuthResponse = {
  token: string;
  expires_at: string;
};

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private loggedIn = false;
  private loggedInSubject = new Subject<boolean>();
  isLoggedIn$ = this.loggedInSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {
    if (this.isLoggedIn()) {
      this.loggedIn = true;
      this.loggedInSubject.next(true);
    }
  }

  login(userData: api.UserLoginDTO) {
    return this.http
      .post('http://localhost:8080/api/user/login', userData)
      .pipe(
        tap((res) => {
          this.loggedIn = true;
          this.loggedInSubject.next(true);
        })
      );
    // this.loggedIn = true;
    // this.loggedInSubject.next(true);
    // .pipe(
    //   tap((response: any) => {
    //     console.log('login response: ', response);
    //     this.setSession(response);
    //     this.router.navigate(['/']);
    //   })
    // );
  }

  register(userData: api.UserRegisterDTO) {
    return this.http.post('http://localhost:8080/api/user/signup', userData);
  }

  public setSession(response: any) {
    // console.log('zalogowany: ', response);
    localStorage.setItem('JWT_TOKEN', response.token);
    localStorage.setItem('expires_at', response.expires_at);
  }

  logout() {
    localStorage.removeItem('JWT_TOKEN');
    localStorage.removeItem('expires_at');
    this.loggedIn = false;
    this.loggedInSubject.next(false);
    this.router.navigate(['/home']);
  }

  public isLoggedIn() {
    // console.log('is logged?');
    const JWT_TOKEN = localStorage.getItem('JWT_TOKEN');
    const expires_at = localStorage.getItem('expires_at');
    if (JWT_TOKEN && expires_at) {
      // console.log('zalogowany');
      return moment().isBefore(this.getExpiration());
    }
    // console.log('wylogowany');
    return false;
  }

  isLoggedOut() {
    return !this.isLoggedIn();
  }

  getExpiration() {
    const expires_at = localStorage.getItem('expires_at');
    if (expires_at) {
      return moment(expires_at);
    }

    return null;
  }

  getToken() {
    return localStorage.getItem('JWT_TOKEN');
  }
}

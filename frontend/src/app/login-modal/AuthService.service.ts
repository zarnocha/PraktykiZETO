import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ChangeDetectorRef, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { Subject, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
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
        catchError((error) => {
          console.log('error: ', error);
          console.log('message: ', error.error.message);
          if (error.error && error.error.message) {
            return throwError(() => new Error(error.error.message));
          }

          return throwError(() => new Error('Wystąpił błąd z połączeniem.'));
        }),

        tap((res) => {
          this.loggedIn = true;
          this.loggedInSubject.next(true);
        })
      );
  }

  register(userData: api.UserRegisterDTO) {
    return this.http
      .post('http://localhost:8080/api/user/signup', userData)
      .pipe(
        catchError((error) => {
          console.log('error: ', error);
          console.log('message: ', error.error.message);
          if (error.error && error.error.message) {
            return throwError(() => new Error(error.error.message));
          }

          return throwError(() => new Error('Wystąpił błąd z połączeniem.'));
        })
      );
  }

  public setSession(response: any) {
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
    const JWT_TOKEN = localStorage.getItem('JWT_TOKEN');
    const expires_at = localStorage.getItem('expires_at');
    if (JWT_TOKEN && expires_at) {
      return moment().isBefore(this.getExpiration());
    }
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

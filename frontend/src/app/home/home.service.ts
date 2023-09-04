import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class HomeService {
  constructor(private http: HttpClient) {}

  getCars(): Observable<any> {
    let headers = new HttpHeaders();
    const url = 'http://localhost:8080/api/car/all';
    return this.http
      .get(url, {
        headers: headers,
      })
      .pipe(
        catchError((error) => {
          return throwError(
            () => new Error('Wystąpił błąd z połączeniem. Spróbuj ponownie.')
          );
        })
      );
  }
}

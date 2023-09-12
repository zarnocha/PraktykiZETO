import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CarInfoService {
  constructor(private http: HttpClient) {}

  getCar(carId: number): Observable<any> {
    let headers: HttpHeaders;
    let url: string;

    headers = new HttpHeaders();
    url = `http://localhost:8080/api/car/${carId}`;

    return this.http
      .get(url, {
        headers: headers,
      })
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
}

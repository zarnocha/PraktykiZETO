import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DialogModalService {
  constructor(private http: HttpClient) {}

  getAvailableFilters(): Observable<any> {
    let headers: HttpHeaders = new HttpHeaders();
    let url: string = 'http://localhost:8080/api/car/availableFilters';

    return this.http
      .get(url, {
        headers: headers,
      })
      .pipe(
        catchError((error) => {
          return throwError(() => new Error('Wystąpił błąd z połączeniem.'));
        })
      );
  }
}

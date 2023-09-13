import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';

function addLeadingZero(number: number) {
  return number < 10 ? `0${number}` : `${number}`;
}

@Injectable({
  providedIn: 'root',
})
export class HomeService {
  constructor(private http: HttpClient) {}

  getCars(queryParams?: any): Observable<any> {
    let headers: HttpHeaders;
    let url: string;

    if (queryParams) {
      const sendingParams = Object.create(queryParams);

      const urlParams = new URLSearchParams();

      for (const key in sendingParams) {
        if (sendingParams[key] !== undefined) {
          if (key === 'from' || key === 'to') {
            const currentDate = sendingParams[key];
            sendingParams[key] = `${currentDate.getFullYear()}-${addLeadingZero(
              currentDate.getMonth() + 1
            )}-${addLeadingZero(currentDate.getDate())}T${addLeadingZero(
              currentDate.getHours()
            )}:${addLeadingZero(currentDate.getMinutes())}:${addLeadingZero(
              currentDate.getSeconds()
            )}`;
          }
          urlParams.set(key, sendingParams[key].toString());
        }
      }

      headers = new HttpHeaders();
      url = 'http://localhost:8080/api/car/filter';
      url = `${url}?${urlParams.toString()}`;
      console.log('url ', url);
    } else {
      headers = new HttpHeaders();
      url = 'http://localhost:8080/api/car/filter';
    }

    return this.http
      .get(url, {
        headers: headers,
      })
      .pipe(
        catchError((error) => {
          console.log('error: ', error);
          return throwError(() => new Error('Wystąpił błąd z połączeniem.'));
        })
      );
  }
}

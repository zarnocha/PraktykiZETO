import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';

type GetPriceParams = {
  carId: number;
  startTime: Date;
  endTime: Date;
};

function addLeadingZero(number: number) {
  return number < 10 ? `0${number}` : `${number}`;
}

@Injectable({
  providedIn: 'root',
})
export class RentFormService {
  constructor(private http: HttpClient) {}

  getPriceForCar(params: GetPriceParams): Observable<any> {
    let headers: HttpHeaders;
    let url: string;

    headers = new HttpHeaders();
    url = `http://localhost:8080/api/car/${params.carId}/price`;

    const sendingParams = Object.create(params);

    const urlParams = new URLSearchParams();

    for (const key in sendingParams) {
      if (sendingParams[key] !== undefined) {
        if (key === 'startTime' || key === 'endTime') {
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

    url = `${url}?${urlParams.toString()}`;

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

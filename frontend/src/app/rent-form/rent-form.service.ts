import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';

type RentParams = {
  carId: number;
  startTime: Date;
  endTime: Date;
};

type SendingParams = {
  carId: number;
  startTime: string;
  endTime: string;
};

function convertDate(data: Date) {
  return `${data.getFullYear()}-${addLeadingZero(
    data.getMonth() + 1
  )}-${addLeadingZero(data.getDate())}T${addLeadingZero(
    data.getHours()
  )}:${addLeadingZero(data.getMinutes())}:${addLeadingZero(data.getSeconds())}`;
}

function addLeadingZero(number: number) {
  return number < 10 ? `0${number}` : `${number}`;
}

@Injectable({
  providedIn: 'root',
})
export class RentFormService {
  constructor(private http: HttpClient) {}

  getPriceForCar(params: RentParams): Observable<any> {
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
          sendingParams[key] = convertDate(currentDate);
        }
        urlParams.set(key, sendingParams[key]);
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

  makeARent(data: RentParams): Observable<any> {
    let headers: HttpHeaders;
    let url: string;

    headers = new HttpHeaders();
    url = `http://localhost:8080/api/rent/add`;

    const sendingParams: SendingParams = {
      carId: data.carId,
      startTime: convertDate(data.startTime),
      endTime: convertDate(data.endTime),
    };

    return this.http
      .post(url, sendingParams, {
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

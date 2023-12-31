import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../login-modal/AuthService.service';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { NgbTimepickerModule } from '@ng-bootstrap/ng-bootstrap';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { RentFormService } from './rent-form.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatTooltipModule } from '@angular/material/tooltip';

type TimeSelector = {
  hour: number;
  minute: number;
};

@Component({
  selector: 'rent-form',
  standalone: true,
  imports: [
    CommonModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatSelectModule,
    NgbTimepickerModule,
    MatNativeDateModule,
    MatDatepickerModule,
    MatInputModule,
    MatButtonModule,
    MatCheckboxModule,
    FormsModule,
    MatTooltipModule,
  ],
  templateUrl: './rent-form.component.html',
  styleUrls: ['./rent-form.component.sass'],
})
export class RentFormComponent implements OnInit {
  @Input() defaultPrice!: api.CarDTO['dayPrice'];

  enabledSubmitionButton: boolean = false;
  carId: number = 0;
  isLoggedIn: boolean = false;
  form!: FormGroup;
  todayDate: Date;

  loading: boolean = false;
  errorMessage: string = '';

  timeFrom: FormControl<TimeSelector | string> = new FormControl('', {
    nonNullable: true,
  });
  timeTo: FormControl<TimeSelector | string> = new FormControl('', {
    nonNullable: true,
  });

  startTime: FormControl<Date | string> = new FormControl('', {
    nonNullable: true,
  });
  endTime: FormControl<Date | string> = new FormControl('', {
    nonNullable: true,
  });
  price: api.CarPriceDTO | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private rentFormService: RentFormService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.todayDate = new Date();

    const routeParams = this.route.snapshot.paramMap;
    this.carId = Number(routeParams.get('id'));

    this.form = this.formBuilder.group({
      startTime: this.startTime,
      endTime: this.endTime,
      timeFrom: [this.timeFrom, [Validators.minLength(4), Validators.required]],
      timeTo: [this.timeTo, [Validators.minLength(4), Validators.required]],
      carId: [this.carId, [Validators.minLength(1)]],
    });
  }

  onSubmit(): void {
    const timeFromValue: TimeSelector = this.form.controls['timeFrom'].value;
    const timeToValue: TimeSelector = this.form.controls['timeTo'].value;
    const finalStartTime: Date = new Date(
      JSON.parse(JSON.stringify(this.form.controls['startTime'].value))
    );
    const finalEndTime: Date = new Date(
      JSON.parse(JSON.stringify(this.form.controls['endTime'].value))
    );

    if (
      !timeFromValue ||
      !timeToValue ||
      isNaN(finalStartTime.getTime()) ||
      isNaN(finalEndTime.getTime()) ||
      !Object.hasOwn(timeFromValue, 'hour') ||
      !Object.hasOwn(timeFromValue, 'minute') ||
      !Object.hasOwn(timeToValue, 'hour') ||
      !Object.hasOwn(timeToValue, 'minute')
    ) {
      return;
    }
    finalStartTime.setHours(timeFromValue.hour);
    finalStartTime.setMinutes(timeFromValue.minute);

    finalEndTime.setHours(timeToValue.hour);
    finalEndTime.setMinutes(timeToValue.minute);

    if (
      confirm(
        'Czy na pewno chcesz wypożyczyć to auto? Po zatwierdzeniu nie ma możliwości anulowania wypożyczenia.'
      )
    ) {
      this.loading = true;
      this.errorMessage = '';

      this.rentFormService
        .makeARent({
          carId: this.carId,
          startTime: finalStartTime,
          endTime: finalEndTime,
        })
        .subscribe({
          next: (data) => {
            this.loading = false;
            this.errorMessage = '';
            this.router.navigate(['/profile']);
          },
          error: (e) => {
            console.error(e);
            this.loading = false;
            this.errorMessage = e.message;
          },
        });
    }
  }

  getPriceForCar(): void {
    const timeFromValue: TimeSelector = this.form.controls['timeFrom'].value;
    const timeToValue: TimeSelector = this.form.controls['timeTo'].value;
    const finalStartTime: Date = new Date(
      JSON.parse(JSON.stringify(this.form.controls['startTime'].value))
    );
    const finalEndTime: Date = new Date(
      JSON.parse(JSON.stringify(this.form.controls['endTime'].value))
    );

    if (
      !timeFromValue ||
      !timeToValue ||
      isNaN(finalStartTime.getTime()) ||
      isNaN(finalEndTime.getTime()) ||
      !Object.hasOwn(timeFromValue, 'hour') ||
      !Object.hasOwn(timeFromValue, 'minute') ||
      !Object.hasOwn(timeToValue, 'hour') ||
      !Object.hasOwn(timeToValue, 'minute')
    ) {
      this.enabledSubmitionButton = false;
      return;
    }

    finalStartTime.setHours(timeFromValue.hour);
    finalStartTime.setMinutes(timeFromValue.minute);

    finalEndTime.setHours(timeToValue.hour);
    finalEndTime.setMinutes(timeToValue.minute);

    this.loading = true;
    this.errorMessage = '';

    this.rentFormService
      .getPriceForCar({
        carId: this!.carId,
        startTime: finalStartTime,
        endTime: finalEndTime,
      })
      .subscribe({
        next: (data) => {
          this.loading = false;

          this.price = data;
          this.enabledSubmitionButton = true;
        },
        error: (e) => {
          console.error(e);
          this.loading = false;
          this.enabledSubmitionButton = false;
          this.errorMessage = e.message;
        },
      });
  }

  resetDate(): void {
    this.startTime.setValue('');
    this.endTime.setValue('');
    this.timeFrom.setValue('');
    this.timeTo.setValue('');

    this.price = null;
    this.enabledSubmitionButton = false;
  }

  ngOnInit() {
    this.authService.isLoggedIn$.subscribe((loggedIn) => {
      this.isLoggedIn = loggedIn;
    });
    this.isLoggedIn = this.authService.isLoggedIn();
  }
}

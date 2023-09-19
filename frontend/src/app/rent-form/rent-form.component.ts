import { Component, OnInit } from '@angular/core';
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
import { ActivatedRoute } from '@angular/router';

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
  ],
  templateUrl: './rent-form.component.html',
  styleUrls: ['./rent-form.component.sass'],
})
export class RentFormComponent implements OnInit {
  carId: number = 0;
  isLoggedIn: boolean = false;
  form!: FormGroup;
  todayDate: Date;
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
    private route: ActivatedRoute
  ) {
    this.todayDate = new Date();
    const routeParams = this.route.snapshot.paramMap;
    this.carId = Number(routeParams.get('id'));

    this.getPriceForCar();

    if (this.startTime !== null && this.endTime !== null) {
      this.form = this.formBuilder.group({
        startTime: this.startTime.setValidators([
          Validators.minLength(4),
          Validators.required,
        ]),
        endTime: this.endTime.setValidators([
          Validators.minLength(4),
          Validators.required,
        ]),
        carId: new FormControl(this.carId, { nonNullable: true }).setValidators(
          [Validators.minLength(1)]
        ),
      });
    }
  }

  getPriceForCar(): void {
    if (
      typeof this.startTime.value !== 'string' &&
      typeof this.endTime.value !== 'string'
    ) {
      this.rentFormService
        .getPriceForCar({
          carId: this!.carId,
          startTime: this.startTime.value,
          endTime: this.endTime.value,
        })
        .subscribe({
          next: (data: api.CarPriceDTO) => {
            console.log('rent form price data: ', data);
            this.price = data;
          },
        });
    }
  }

  resetDate(): void {
    this.startTime.setValue('');
    this.endTime.setValue('');
    this.price = null;
  }

  ngOnInit() {
    this.authService.isLoggedIn$.subscribe((loggedIn) => {
      this.isLoggedIn = loggedIn;
    });
    this.isLoggedIn = this.authService.isLoggedIn();
  }
}

//         startTime: Date;
//         endTime: Date;
//         carId: number;
//         userId: number;

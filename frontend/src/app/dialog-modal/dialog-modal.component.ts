import { Component, Inject, OnInit, Optional } from '@angular/core';
import { CommonModule, Time } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {
  MatDialog,
  MAT_DIALOG_DATA,
  MatDialogRef,
  MatDialogModule,
} from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSliderModule } from '@angular/material/slider';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatRippleModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import {
  NgbDatepickerModule,
  NgbTimepickerModule,
} from '@ng-bootstrap/ng-bootstrap';
import { QueryParamsAndHour } from '../home/home.component';
import { DialogModalService } from './dialog-modal.service';

interface GearboxSelect {
  value: api.Gearbox;
  viewValue: 'Automatyczna' | 'Manualna';
}

interface TimeObject {
  hour: number;
  minute: number;
}

function hourAndMinuteToMilisecond(time: TimeObject) {
  return time && time.hour && time.minute
    ? time.hour * 3_600_000 + time.minute * 60_000
    : 0;
}

function milisecondsToHourAndMinutes(time: number) {
  return;
}

@Component({
  selector: 'dialog-modal',
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatIconModule,
    MatTooltipModule,
    MatCheckboxModule,
    MatSliderModule,
    MatNativeDateModule,
    MatDatepickerModule,
    ReactiveFormsModule,
    MatRippleModule,
    MatSelectModule,

    NgbTimepickerModule,
  ],
  templateUrl: './dialog-modal.component.html',
  styleUrls: ['./dialog-modal.component.sass'],
})
export class DialogModalComponent implements OnInit {
  todayDate: Date;

  filters: api.CarFilterDTO = {};

  showLoader: boolean = false;
  errorMessage: string = '';

  carBrands: string[] = [];
  brandModels: string[] = [];

  constructor(
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<DialogModalComponent>,
    public dialogModalService: DialogModalService,
    @Inject(MAT_DIALOG_DATA) public data: QueryParamsAndHour
  ) {
    this.todayDate = new Date();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onClear(): void {
    this.data.queryParams = { horsePowerFrom: 0, horsePowerTo: 500 };
    this.data.time = {
      from: {
        hour: 0,
        minute: 0,
      },
      to: {
        hour: 23,
        minute: 59,
      },
    };
  }

  fetchAvailableFilters(): void {
    this.filters = {};
    this.showLoader = true;

    this.dialogModalService.getAvailableFilters().subscribe({
      next: (data: api.CarFilterDTO) => {
        this.showLoader = false;
        this.errorMessage = '';
        this.filters = data;
        console.log(data);
        this.data.queryParams.horsePowerFrom = this.filters.minHorsepower;
        this.data.queryParams.horsePowerTo = this.filters.maxHorsepower;
        this.carBrands = this.filters.brands!.map((brand) => brand.brand);
      },
      error: (e) => {
        console.error(e);
        this.showLoader = false;
        this.errorMessage = e.message;
      },
    });
  }

  getBrandModels(): void {
    const selectedCar = this.data.queryParams.brand;
    console.log('selectedCar: ', selectedCar);

    const foundCar = this.filters.brands!.find(
      (car) => car.brand === selectedCar
    );

    if (foundCar) {
      this.brandModels = foundCar.models;
    } else {
      this.brandModels = [];
    }

    // this.brandModels = this.filters.brands!.find((car) => {
    //   if (car.brand === selectedCar) {
    //     return car.models;
    //   }
    // });

    // this.brandModels = this.filters.brands!.map((car) => {
    //   if (car.brand === selectedCar) {
    //     return car.models;
    //   }
    // });
  }

  ngOnInit(): void {
    this.showLoader = true;
    this.errorMessage = '';
    this.fetchAvailableFilters();
  }
}

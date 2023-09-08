import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
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
import { NgbTimepickerModule } from '@ng-bootstrap/ng-bootstrap';
import { QueryParamsAndHour } from '../home/home.component';
import { DialogModalService } from './dialog-modal.service';

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

  cars?: api.CarBrandModelDTO[] = [];

  constructor(
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<DialogModalComponent>,
    public dialogModalService: DialogModalService,
    @Inject(MAT_DIALOG_DATA) public data: QueryParamsAndHour
  ) {
    this.todayDate = new Date();
    this.fetchAvailableFilters();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onClear(): void {
    this.data.queryParams = {
      horsePowerFrom: this.filters.minHorsepower,
      horsePowerTo: this.filters.maxHorsepower,
    };
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
    this.showLoader = true;

    this.dialogModalService.getAvailableFilters().subscribe({
      next: (data: api.CarFilterDTO) => {
        this.showLoader = false;
        this.errorMessage = '';
        this.filters = data;
        this.cars = this.filters.brands;
      },
      error: (e) => {
        console.error(e);
        this.showLoader = false;
        this.errorMessage = e.message;
      },
    });
  }

  ngOnInit(): void {
    this.showLoader = true;
    this.errorMessage = '';
  }
}

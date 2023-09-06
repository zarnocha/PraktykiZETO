import { Component, OnInit } from '@angular/core';
import { CarCardComponent } from '../car-card/car-card.component';
import { HomeService } from './home.service';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { FormsModule } from '@angular/forms';
import { CarListElementComponent } from '../car-list-element/car-list-element.component';
// import {
//   ProgressSpinnerMode,
//   MatProgressSpinnerModule,
//   MatProgressSpinner,
// } from '@angular/material/progress-spinner';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatButtonModule } from '@angular/material/button';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { DialogModalComponent } from '../dialog-modal/dialog-modal.component';
import { MatDialogModule } from '@angular/material/dialog';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CarCardComponent,
    CarListElementComponent,
    CommonModule,
    DialogModalComponent,
    MatButtonModule,
    MatTooltipModule,
    MatIconModule,
    MatButtonToggleModule,
    MatIconModule,
    FormsModule,
    MatGridListModule,
    MatDialogModule,
  ],

  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass'],
})
export class HomeComponent implements OnInit {
  constructor(public homeService: HomeService, public dialog: MatDialog) {}
  cars: api.CarListDTO[] = [];
  showLoader: boolean = false;
  errorMessage: string = '';
  public viewMode: 'list' | 'grid' = 'grid';

  dialogData: api.CarListQueryParamsDTO = {
    horsePowerFrom: 0,
    horsePowerTo: 200,
  };

  openDialog(): void {
    const dialogRef = this.dialog.open(DialogModalComponent, {
      data: {
        available: this.dialogData.available,
        brand: this.dialogData.brand,
        drive: this.dialogData.drive,
        model: this.dialogData.model,
        from: this.dialogData.from,
        to: this.dialogData.to,
        gearbox: this.dialogData.gearbox,
        horsePowerFrom: this.dialogData.horsePowerFrom,
        horsePowerTo: this.dialogData.horsePowerTo,
      },
    });

    dialogRef.afterClosed().subscribe((result: api.CarListQueryParamsDTO) => {
      if (result) {
        this.dialogData.available = result.available
          ? result.available
          : undefined;
        this.dialogData.brand = result.brand ? result.brand : undefined;
        this.dialogData.drive = result.drive ? result.drive : undefined;
        this.dialogData.model = result.model ? result.model : undefined;

        this.dialogData.from = result.from ? result.from : undefined;

        this.dialogData.to = result.to ? result.to : undefined;

        this.dialogData.gearbox = result.gearbox ? result.gearbox : undefined;

        this.dialogData.horsePowerFrom =
          result.horsePowerFrom === 0 || result.horsePowerFrom
            ? result.horsePowerFrom
            : undefined;
        this.dialogData.horsePowerTo = result.horsePowerTo
          ? result.horsePowerTo
          : undefined;

        console.log(result);
      }
    });
  }

  fetchCarData(): void {
    this.showLoader = true;
    this.errorMessage = '';
    this.homeService.getCars().subscribe({
      next: (carData: Array<api.CarListDTO>) => {
        this.cars = carData;
        console.log(carData);
        this.showLoader = false;
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
    this.fetchCarData();
  }
}

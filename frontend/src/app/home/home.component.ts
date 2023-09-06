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
import {
  DialogData,
  DialogModalComponent,
} from '../dialog-modal/dialog-modal.component';
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

  dialogData: DialogData = { horsePowerFrom: 50, horsePowerTo: 150 };

  openDialog(): void {
    const dialogRef = this.dialog.open(DialogModalComponent, {
      data: {
        horsePowerFrom: this.dialogData.horsePowerFrom,
        horsePowerTo: this.dialogData.horsePowerTo,
        model: this.dialogData.model,
        brand: this.dialogData.brand,
        available: this.dialogData.available,
      },
    });

    dialogRef.afterClosed().subscribe((result: DialogData) => {
      console.log('The dialog was closed');
      if (result) {
        this.dialogData.brand = result.brand;
        this.dialogData.model = result.model;
        this.dialogData.available = result.available;
        this.dialogData.horsePowerFrom = result.horsePowerFrom;
        this.dialogData.horsePowerTo = result.horsePowerTo;
        console.log(result);
      }
    });
  }

  fetchCarData(): void {
    this.showLoader = true;
    this.errorMessage = '';
    this.homeService.getCars().subscribe({
      next: (v: Array<api.CarListDTO>) => {
        this.cars = v;
        console.log(v);
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

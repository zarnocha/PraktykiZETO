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
import { MatSelectModule } from '@angular/material/select';
// import * as moment from 'moment';

export interface QueryParamsAndHour {
  queryParams: api.CarListQueryParamsDTO;
  time: {
    from: {
      hour: number;
      minute: number;
    };
    to: {
      hour: number;
      minute: number;
    };
  };
}

type SortType = 'domyślnie' | 'alfabetycznie ↑' | 'alfabetycznie ↓';

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
    MatSelectModule,
  ],

  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass'],
})
export class HomeComponent implements OnInit {
  constructor(public homeService: HomeService, public dialog: MatDialog) {}

  cars: api.CarListDTO[] = [];
  carsSorted: api.CarListDTO[] = [];

  showLoader: boolean = false;
  errorMessage: string = '';
  public viewMode: 'list' | 'grid' = 'grid';

  selectedSort: SortType = 'domyślnie';
  sorts: SortType[] = ['domyślnie', 'alfabetycznie ↓', 'alfabetycznie ↑'];

  dialogData: QueryParamsAndHour = {
    queryParams: {
      available: true,
    },
    time: {
      from: {
        hour: 0,
        minute: 0,
      },
      to: {
        hour: 23,
        minute: 59,
      },
    },
  };

  openDialog(): void {
    const dialogRef = this.dialog.open(DialogModalComponent, {
      data: {
        queryParams: {
          available: this.dialogData.queryParams.available,
          brand: this.dialogData.queryParams.brand,
          drive: this.dialogData.queryParams.drive,
          model: this.dialogData.queryParams.model,
          from: this.dialogData.queryParams.from,
          to: this.dialogData.queryParams.to,
          gearbox: this.dialogData.queryParams.gearbox,
          horsePowerFrom: this.dialogData.queryParams.horsePowerFrom,
          horsePowerTo: this.dialogData.queryParams.horsePowerTo,
        },
        time: { from: this.dialogData.time.from, to: this.dialogData.time.to },
      },
    });

    dialogRef.afterClosed().subscribe((result: QueryParamsAndHour) => {
      if (result && result.queryParams && result.time.from && result.time.to) {
        this.dialogData.queryParams.available = result.queryParams.available
          ? result.queryParams.available
          : undefined;
        this.dialogData.queryParams.brand = result.queryParams.brand
          ? result.queryParams.brand
          : undefined;
        this.dialogData.queryParams.drive = result.queryParams.drive
          ? result.queryParams.drive
          : undefined;
        this.dialogData.queryParams.model = result.queryParams.model
          ? result.queryParams.model
          : undefined;

        this.dialogData.queryParams.from = result.queryParams.from
          ? result.queryParams.from
          : undefined;

        this.dialogData.queryParams.to = result.queryParams.to
          ? result.queryParams.to
          : undefined;

        this.dialogData.time.from = result.time.from;
        this.dialogData.time.to = result.time.to;

        console.log(this.dialogData.queryParams.from);
        console.log(this.dialogData.queryParams.to);

        this.dialogData.queryParams.from?.setHours(
          result.time.from.hour,
          result.time.from.minute
        );

        this.dialogData.queryParams.to?.setHours(
          result.time.to.hour,
          result.time.to.minute
        );

        this.dialogData.queryParams.gearbox = result.queryParams.gearbox
          ? result.queryParams.gearbox
          : undefined;

        this.dialogData.queryParams.horsePowerFrom =
          result.queryParams.horsePowerFrom === 0 ||
          result.queryParams.horsePowerFrom
            ? result.queryParams.horsePowerFrom
            : undefined;
        this.dialogData.queryParams.horsePowerTo = result.queryParams
          .horsePowerTo
          ? result.queryParams.horsePowerTo
          : undefined;

        this.fetchCarData(this.dialogData.queryParams);
      }
    });
  }

  fetchCarData(queryParams?: api.CarListQueryParamsDTO): void {
    this.showLoader = true;
    this.errorMessage = '';
    this.cars = [];

    if (queryParams) {
      this.homeService.getCars(queryParams).subscribe({
        next: (carData: Array<api.CarListDTO>) => {
          this.cars = carData;
          this.carsSorted = this.cars.slice();

          this.showLoader = false;
        },
        error: (e) => {
          console.error(e);
          this.showLoader = false;
          this.errorMessage = e.message;
        },
      });
    } else {
      queryParams = {};
      this.homeService.getCars().subscribe({
        next: (carData: Array<api.CarListDTO>) => {
          console.log('carData: ', carData);
          this.cars = carData;
          this.carsSorted = this.cars.slice();
          this.showLoader = false;
        },
        error: (e) => {
          console.error(e);
          this.showLoader = false;
          this.errorMessage = e.message;
        },
      });
    }
  }

  onSortChange() {
    console.log(this.selectedSort);

    if (this.selectedSort === 'alfabetycznie ↓') {
      this.carsSorted!.sort((a, b) => {
        const brandA = a.brand.toLowerCase();
        const brandB = b.brand.toLowerCase();

        if (brandA < brandB) {
          return -1;
        }
        if (brandA > brandB) {
          return 1;
        }

        if (brandA == brandB) {
          const brandAModel = a.model.toLowerCase();
          const brandBModel = b.model.toLowerCase();
          if (brandAModel < brandBModel) {
            return -1;
          }
          if (brandAModel > brandBModel) {
            return 1;
          }
        }
        return 0;
      });
    } else if (this.selectedSort === 'alfabetycznie ↑') {
      this.carsSorted!.sort((a, b) => {
        const brandA = a.brand.toLowerCase();
        const brandB = b.brand.toLowerCase();

        if (brandA < brandB) {
          return 1;
        }
        if (brandA > brandB) {
          return -1;
        }

        if (brandA == brandB) {
          const brandAModel = a.model.toLowerCase();
          const brandBModel = b.model.toLowerCase();
          if (brandAModel < brandBModel) {
            return 1;
          }
          if (brandAModel > brandBModel) {
            return -1;
          }
        }
        return 0;
      });
    } else if (this.selectedSort === 'domyślnie') {
      this.carsSorted!.sort((a, b) => {
        const brandA = a.id;
        const brandB = b.id;

        if (brandA < brandB) {
          return -1;
        }
        if (brandA > brandB) {
          return 1;
        }
        return 0;
      });
    }
  }

  ngOnInit(): void {
    this.showLoader = true;
    this.fetchCarData();
  }
}

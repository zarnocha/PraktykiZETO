import { Component, OnInit } from '@angular/core';
import { CarCardComponent } from '../car-card/car-card.component';
import { HomeService } from './home.service';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { FormsModule } from '@angular/forms';
import { CarListElementComponent } from '../car-list-element/car-list-element.component';
import {
  ProgressSpinnerMode,
  MatProgressSpinnerModule,
  MatProgressSpinner,
} from '@angular/material/progress-spinner';
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CarCardComponent,
    CarListElementComponent,
    CommonModule,
    MatButtonToggleModule,
    MatIconModule,
    FormsModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass'],
})
export class HomeComponent implements OnInit {
  cars: endpoint.Car[] = [];
  showLoader: boolean = false;
  errorMessage: string = '';
  public viewMode: 'list' | 'grid' = 'grid';
  constructor(private homeService: HomeService) {}

  fetchCarData(): void {
    this.showLoader = true;
    this.homeService.getCars().subscribe({
      next: (v: Array<endpoint.Car>) => {
        this.cars = v;
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

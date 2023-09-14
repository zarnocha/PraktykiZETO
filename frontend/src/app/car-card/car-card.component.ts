import { Component, Input, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'car-card',
  standalone: true,
  imports: [
    CommonModule,
    MatIconModule,
    MatButtonModule,
    MatCardModule,
    MatGridListModule,
    RouterModule,
  ],
  templateUrl: './car-card.component.html',
  styleUrls: ['./car-card.component.sass'],
  encapsulation: ViewEncapsulation.None,
})
export class CarCardComponent {
  @Input()
  carData!: api.CarDTO;
}

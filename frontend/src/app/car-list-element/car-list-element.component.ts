import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'car-list-element',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatButtonModule],

  templateUrl: './car-list-element.component.html',
  styleUrls: ['./car-list-element.component.sass'],
})
export class CarListElementComponent {
  @Input()
  carData!: api.CarListDTO;
}

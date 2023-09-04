import { Component, Input, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'car-card',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatButtonModule, MatCardModule],
  templateUrl: './car-card.component.html',
  styleUrls: ['./car-card.component.sass'],
  encapsulation: ViewEncapsulation.None,
})
export class CarCardComponent {
  @Input()
  carData!: api.CarEntity;
  // imageSource: SafeResourceUrl | undefined;
  // constructor(private sanitizer: DomSanitizer) {}

  // ngOnInit() {
  //   this.imageSource = this.sanitizer.bypassSecurityTrustResourceUrl(
  //     `data:image/png;base64, ${this.carData.picture}`
  //   );
  // }
}

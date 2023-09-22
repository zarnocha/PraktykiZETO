import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';

import { MatIconModule, MatIconRegistry } from '@angular/material/icon';
import { CarInfoService } from './car-info.service';
import { DomSanitizer } from '@angular/platform-browser';
import { RentFormComponent } from '../rent-form/rent-form.component';

@Component({
  selector: 'car-info',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatIconModule,
    MatButtonModule,
    RentFormComponent,
  ],
  templateUrl: './car-info.component.html',
  styleUrls: ['./car-info.component.sass'],
})
export class CarInfoComponent implements OnInit {
  carId!: number;
  car!: api.CarDTO;
  showLoader: boolean = false;
  errorMessage: string = '';

  getCarData(): void {
    this.showLoader = true;
    this.errorMessage = '';

    this.carInfoService.getCar(this.carId).subscribe({
      next: (carData: api.CarDTO) => {
        this.car = carData;
        this.showLoader = false;
      },
      error: (e) => {
        console.error(e);
        this.showLoader = false;
        this.errorMessage = e.message;
      },
    });
  }

  constructor(
    private carInfoService: CarInfoService,
    private route: ActivatedRoute,
    private matIconRegistry: MatIconRegistry,
    private domSanitizer: DomSanitizer
  ) {
    this.matIconRegistry.addSvgIcon(
      'axle-drive',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../../assets/icons/axle-icon/axle-icon.svg'
      )
    );
  }
  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    this.carId = Number(routeParams.get('id'));
    this.getCarData();
  }
}

import {
  trigger,
  state,
  style,
  transition,
  animate,
} from '@angular/animations';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { ProfileService } from './profile.service';
import { MatTooltipModule } from '@angular/material/tooltip';

export interface Table {
  startTime: string;
  endTime: string;

  price: string;
  brand: string;
  model: string;
  picture: string;
  expanded: boolean;
}

@Component({
  selector: 'profile',
  imports: [
    CommonModule,
    MatTableModule,
    NgFor,
    MatButtonModule,
    NgIf,
    MatIconModule,
    MatTooltipModule,
  ],
  templateUrl: './profile.component.html',
  standalone: true,
  styleUrls: ['./profile.component.sass'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition(
        'expanded <=> collapsed',
        animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')
      ),
    ]),
  ],
})
export class ProfileComponent {
  showLoader: boolean = false;
  expandedRows: boolean = false;

  errorMessage: string = '';
  wholeProfileData!: api.UserProfileWithRentsDTO;
  profileData!: api.UserProfileDTO;
  dataSource!: Table[];
  ELEMENT_DATA!: api.UserProfileWithRentsDTO['rents'];

  constructor(public profileService: ProfileService) {
    this.getProfileData();
  }

  columnsToDisplay = ['startTime', 'endTime', 'price', 'expandButton'];

  toggleRow(element: { expanded: boolean }) {
    // this.dataSource.forEach((row) => {
    //   row.expanded = false;
    // });
    element.expanded = !element.expanded;
  }

  manageAllRows(flag: boolean) {
    this.expandedRows = flag;

    this.dataSource.forEach((row) => {
      row.expanded = flag;
    });
  }

  getProfileData = (): void => {
    this.profileService.getUserProfile().subscribe({
      next: (data: api.UserProfileWithRentsDTO) => {
        this.wholeProfileData = data;

        this.profileData = data.profile;

        this.ELEMENT_DATA = data.rents;

        this.showLoader = false;

        this.dataSource = this.ELEMENT_DATA.map((item) => {
          console.log('item: ', item);
          return {
            startTime: new Date(item.startTime).toLocaleString(),
            endTime: new Date(item.endTime).toLocaleString(),
            price: item.price.toFixed(2),
            brand: item.car.brand,
            model: item.car.model,
            picture: item.car.picture,
            expanded: false,
          };
        });
      },
      error: (e) => {
        console.error(e);
        this.showLoader = false;
        this.errorMessage = e.message;
      },
    });
  };
}

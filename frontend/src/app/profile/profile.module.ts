import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileComponent } from './profile/profile.component';
import { CarCardComponent } from '../car-card/car-card.component';

@NgModule({
  declarations: [ProfileComponent],
  imports: [CommonModule],
})
export class ProfileModule {}

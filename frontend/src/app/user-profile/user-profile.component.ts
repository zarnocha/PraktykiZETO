import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'user-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.sass'],
})
export class UserProfileComponent {}

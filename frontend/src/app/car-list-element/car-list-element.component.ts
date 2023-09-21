import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterModule } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from '../login-modal/AuthService.service';
import { LoginModalComponent } from '../login-modal/login-modal.component';

@Component({
  selector: 'car-list-element',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatButtonModule, RouterModule],

  templateUrl: './car-list-element.component.html',
  styleUrls: ['./car-list-element.component.sass'],
})
export class CarListElementComponent implements OnInit {
  @Input()
  carData!: api.CarDTO;

  isLoggedIn: boolean = false;

  constructor(
    public dialog: MatDialog,
    private authService: AuthService,
    public router: Router
  ) {
    authService.isLoggedIn$.subscribe((loggedIn) => {
      this.isLoggedIn = loggedIn;
    });
  }

  openDialog(): void {
    this.dialog.open(LoginModalComponent);
  }

  ngOnInit() {
    this.authService.isLoggedIn$.subscribe((loggedIn) => {
      this.isLoggedIn = loggedIn;
    });

    this.isLoggedIn = this.authService.isLoggedIn();
  }
}

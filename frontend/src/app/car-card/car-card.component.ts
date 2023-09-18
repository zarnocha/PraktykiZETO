import { Component, Input, OnInit, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { BrowserModule } from '@angular/platform-browser';
import { Router, RouterModule } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from '../login-modal/AuthService.service';
import { LoginModalComponent } from '../login-modal/login-modal.component';

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
export class CarCardComponent implements OnInit {
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
    // this.isLoggedIn = authService.isLoggedIn();
  }

  openDialog(): void {
    console.log(this.isLoggedIn);
    this.dialog.open(LoginModalComponent);
  }

  ngOnInit() {
    this.authService.isLoggedIn$.subscribe((loggedIn) => {
      this.isLoggedIn = loggedIn;
    });
    // console.log('onInit: ', this.isLoggedIn);
    // this.authService.isLoggedIn$.subscribe((loggedIn) => {
    //   console.log('onInit fn: ', loggedIn);
    // });

    this.isLoggedIn = this.authService.isLoggedIn();
  }
}

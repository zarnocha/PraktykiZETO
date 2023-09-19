import { CommonModule } from '@angular/common';
import {
  ChangeDetectorRef,
  Component,
  OnInit,
  ViewEncapsulation,
} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {
  ActivatedRoute,
  NavigationEnd,
  Router,
  RouterModule,
} from '@angular/router';
import { filter } from 'rxjs/operators';
import { AppRoutingModule } from '../app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTabsModule } from '@angular/material/tabs';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatButtonModule } from '@angular/material/button';
import { SearchbarComponent } from '../searchbar/searchbar.component';
import { MatDialog } from '@angular/material/dialog';
import { LoginModalComponent } from '../login-modal/login-modal.component';
import { AuthService } from '../login-modal/AuthService.service';
import { MatMenuModule } from '@angular/material/menu';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  standalone: true,
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    BrowserAnimationsModule,
    MatTabsModule,
    MatIconModule,
    MatTooltipModule,
    MatButtonModule,
    SearchbarComponent,
    MatMenuModule,
  ],
  styleUrls: ['./navbar.component.sass'],
  encapsulation: ViewEncapsulation.None,
})
export class NavbarComponent implements OnInit {
  activeRouteTitle: string = '';
  loginModalShown: boolean = false;
  isLoggedIn: boolean = false;

  constructor(
    public router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private authService: AuthService,
    private changeDetectorRef: ChangeDetectorRef
  ) {
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => {
        const childRoute = this.route.firstChild;
        this.activeLink = this.router.url;
        if (childRoute) {
          this.activeRouteTitle = childRoute.snapshot.data['title'] || '';
        }
      });
  }
  routes = this.router.config;
  activeLink = this.router.url;

  openDialog(): void {
    this.dialog.open(LoginModalComponent);
  }
  logOut(): void {
    this.changeDetectorRef.detectChanges();
    this.authService.logout();
  }

  ngOnInit() {
    this.authService.isLoggedIn$.subscribe((loggedIn) => {
      this.isLoggedIn = loggedIn;
    });
    this.isLoggedIn = this.authService.isLoggedIn();
  }
}

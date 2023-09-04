import { CommonModule } from '@angular/common';
import { Component, ViewEncapsulation } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs/operators';
import { AppRoutingModule } from '../app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTabsModule } from '@angular/material/tabs';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatButtonModule } from '@angular/material/button';
import { SearchbarComponent } from '../searchbar/searchbar.component';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  standalone: true,
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatTabsModule,
    MatIconModule,
    MatTooltipModule,
    MatButtonModule,
    SearchbarComponent,
  ],
  styleUrls: ['./navbar.component.sass'],
  encapsulation: ViewEncapsulation.None,
})
export class NavbarComponent {
  activeRouteTitle: string = '';

  constructor(private router: Router, private route: ActivatedRoute) {
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
}

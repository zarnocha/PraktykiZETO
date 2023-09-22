import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.sass'],
  standalone: true,
})
export class AboutComponent {
  url = 'about';
  constructor(private router: Router) {
    this.url = router.url.replace('/', '');
  }
}

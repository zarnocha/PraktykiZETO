import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { AboutComponent } from './about/about.component';

const routes: Routes = [
  {
    path: 'home',
    loadComponent: () =>
      import('./home/home.component').then((comp) => comp.HomeComponent),
    data: { title: 'Strona główna' },
  },
  {
    path: 'locations',
    loadComponent: () =>
      import('./profile/profile.component').then((m) => m.ProfileComponent),
    data: { title: 'Lokalizacje' },
  },
  {
    path: 'cars',
    loadComponent: () =>
      import('./profile/profile.component').then((m) => m.ProfileComponent),
    data: { title: 'Flota' },
  },
  {
    path: 'opinions',
    loadComponent: () =>
      import('./about/about.component').then((m) => m.AboutComponent),
    data: { title: 'Opinie' },
  },
  {
    path: 'about',
    loadComponent: () =>
      import('./about/about.component').then((m) => m.AboutComponent),
    data: { title: 'O nas' },
  },
  { path: '*', redirectTo: 'home' },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginModalComponent } from './login-modal/login-modal.component';
import { AppComponent } from './app.component';

const routes: Routes = [
  {
    path: 'home',
    loadComponent: () =>
      import('./home/home.component').then((comp) => comp.HomeComponent),
    data: { title: 'Strona główna' },
  },
  // {
  //   path: 'login',
  //   component: LoginModalComponent,
  //   providers: [AppComponent],
  //   outlet: 'login',
  // },
  {
    path: 'locations',
    loadComponent: () =>
      import('./profile/profile.component').then(
        (comp) => comp.ProfileComponent
      ),
    data: { title: 'Lokalizacje' },
  },
  {
    path: 'cars',
    loadComponent: () =>
      import('./profile/profile.component').then(
        (comp) => comp.ProfileComponent
      ),
    data: { title: 'Flota' },
  },
  {
    path: 'opinions',
    loadComponent: () =>
      import('./about/about.component').then((comp) => comp.AboutComponent),
    data: { title: 'Opinie' },
  },
  {
    path: 'about',
    loadComponent: () =>
      import('./about/about.component').then((comp) => comp.AboutComponent),
    data: { title: 'O nas' },
  },
  {
    path: 'car/:id',
    loadComponent: () =>
      import('./car-info/car-info.component').then(
        (comp) => comp.CarInfoComponent
      ),
  },

  { path: '*', redirectTo: 'home' },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

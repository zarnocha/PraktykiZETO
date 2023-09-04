import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile/profile.component';
import { AboutComponent } from './about/about/about.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent, data: { title: 'Strona główna' } },
  {
    path: 'locations',
    component: ProfileComponent,
    data: { title: 'Lokalizacje' },
  },
  { path: 'cars', component: ProfileComponent, data: { title: 'Flota' } },
  { path: 'opinions', component: AboutComponent, data: { title: 'Opinie' } },
  { path: 'about', component: AboutComponent, data: { title: 'O nas' } },
  { path: '*', redirectTo: 'home' },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

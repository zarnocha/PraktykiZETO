import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgFor, AsyncPipe } from '@angular/common';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';

export interface MainGroup {
  name: string;
  subgroups: SubGroup[];
}

export interface SubGroup {
  letter: string;
  names: string[];
}

export const _filter = (opt: string[], value: string): string[] => {
  const filterValue = value.toLowerCase();
  return opt.filter((item) => item.toLowerCase().includes(filterValue));
};

@Component({
  selector: 'searchbar',
  templateUrl: './searchbar.component.html',
  styleUrls: ['./searchbar.component.sass'],
  standalone: true,
  imports: [
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatAutocompleteModule,
    ReactiveFormsModule,
    NgFor,
    MatIconModule,
    AsyncPipe,
  ],
})
export class SearchbarComponent implements OnInit {
  stateForm = this._formBuilder.group({
    stateGroup: '',
  });

  carNames: string[] = [
    'Abarth',
    'Acura',
    'Aixam',
    'Alfa Romeo',
    'Alpine',
    'Aro',
    'Asia',
    'Aston Martin',
    'Audi',
    'Austin',
    'Autobianchi',
    'BAC',
    'Baic',
    'Bentley',
    'BMW',
    'BMW-ALPINA',
    'Bolloré',
    'Brilliance',
    'Bugatti',
    'Buick',
    'BYD',
    'Cadillac',
    'Casalini',
    'Caterham',
    'Cenntro',
    'Chatenet',
    'Chevrolet',
    'Chrysler',
    'Citroën',
    'Cupra',
    'Dacia',
    'Daewoo',
    'Daihatsu',
    'DeLorean',
    'DFSK',
    'DKW',
    'Dodge',
    'DR MOTOR',
    'DS Automobiles',
    'Eagle',
    'FAW',
    'Ferrari',
    'Fiat',
    'Ford',
    'Galloper',
    'Gaz',
    'Geely',
    'Genesis',
    'GMC',
    'Grecav',
    'GWM',
    'Hammer',
    'Holden',
    'Honda',
    'Hongqi',
    'Hummer',
    'Hyundai',
    'Ineos',
    'Infiniti',
    'Inny',
    'Isuzu',
    'Iveco',
    'Jaguar',
    'Jeep',
    'Jetour',
    'John Deere',
    'Kaipan',
    'Kia',
    'KTM',
    'Lada',
    'Lamborghini',
    'Lancia',
    'Land Rover',
    'LEVC',
    'Lexus',
    'Ligier',
    'Lincoln',
    'Lotus',
    'LTI',
    'LuAZ',
    'Lucid',
    'MAN',
    'Maserati',
    'Maxus',
    'Maybach',
    'Mazda',
    'McLaren',
    'Mercedes-Benz',
    'Mercury',
    'MG',
    'Microcar',
    'MINI',
    'Mitsubishi',
    'NIO',
    'Nissan',
    'NSU',
    'Nysa',
    'Oldsmobile',
    'Opel',
    'Peugeot',
    'Piaggio',
    'Plymouth',
    'Polestar',
    'Polonez',
    'Pontiac',
    'Porsche',
    'Proton',
    'RAM',
    'Rayton Fissore',
    'Renault',
    'Rolls-Royce',
    'Rover',
    'Saab',
    'Santana',
    'Seat',
    'Seres',
    'Shuanghuan',
    'Skoda',
    'Skywell',
    'Smart',
    'SsangYong',
    'Subaru',
    'Suzuki',
    'Syrena',
    'Talbot',
    'Tarpan',
    'Tata',
    'Tatra',
    'Tavria',
    'Tesla',
    'Toyota',
    'Trabant',
    'Triumph',
    'Uaz',
    'Vanderhall',
    'Vauxhall',
    'VELEX',
    'Volkswagen',
    'Volvo',
    'Warszawa',
    'Wartburg',
    'Wołga',
    'Yugo',
    'Zaporożec',
    'Zastava',
    'Żuk',
  ];

  mainGroups: MainGroup[] = [
    {
      name: 'Marki samochodów',
      subgroups: [],
    },
    {
      name: 'Użytkownicy',
      subgroups: [
        // Dodaj podgrupy użytkowników
      ],
    },
    {
      name: 'Opcje',
      subgroups: [
        // Dodaj podgrupy opcji
      ],
    },
  ];

  groupOptions: Observable<MainGroup[]> | undefined;

  constructor(private _formBuilder: FormBuilder) {}

  ngOnInit() {
    this.carNames.forEach((name) => {
      const firstLetter = name.charAt(0).toUpperCase();
      const mainGroup = this.mainGroups.find(
        (group) => group.name === 'Marki samochodów'
      );
      if (mainGroup) {
        let subgroup = mainGroup.subgroups.find(
          (sub) => sub.letter === firstLetter
        );
        if (!subgroup) {
          subgroup = { letter: firstLetter, names: [] };
          mainGroup.subgroups.push(subgroup);
        }
        subgroup.names.push(name);
      }
    });

    this.groupOptions = this.stateForm.get('stateGroup')!.valueChanges.pipe(
      startWith(''),
      map((value) => this._filterGroups(value || ''))
    );
  }

  private _filterGroups(value: string): MainGroup[] {
    if (value) {
      const filteredGroups: MainGroup[] = [];
      this.mainGroups.forEach((mainGroup) => {
        const filteredSubgroups: SubGroup[] = mainGroup.subgroups
          .map((subgroup) => {
            const filteredNames = _filter(subgroup.names, value);
            return { letter: subgroup.letter, names: filteredNames };
          })
          .filter((subgroup) => subgroup.names.length > 0);

        if (filteredSubgroups.length > 0) {
          filteredGroups.push({
            name: mainGroup.name,
            subgroups: filteredSubgroups,
          });
        }
      });
      return filteredGroups;
    }
    return this.mainGroups;
  }
}

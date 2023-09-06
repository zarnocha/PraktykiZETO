import { Component, Inject, Optional } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {
  MatDialog,
  MAT_DIALOG_DATA,
  MatDialogRef,
  MatDialogModule,
} from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSliderModule } from '@angular/material/slider';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatRippleModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';

interface GearboxSelect {
  value: api.Gearbox;
  viewValue: 'Automatyczna' | 'Manualna';
}

@Component({
  selector: 'dialog-modal',
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatIconModule,
    MatTooltipModule,
    MatCheckboxModule,
    MatSliderModule,
    MatNativeDateModule,
    MatDatepickerModule,
    ReactiveFormsModule,
    MatRippleModule,
    MatSelectModule,
  ],

  templateUrl: './dialog-modal.component.html',
  styleUrls: ['./dialog-modal.component.sass'],
})
export class DialogModalComponent {
  constructor(
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<DialogModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: api.CarListQueryParamsDTO
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  onClear(): void {
    this.data = { horsePowerFrom: 0, horsePowerTo: 500 };
  }
}

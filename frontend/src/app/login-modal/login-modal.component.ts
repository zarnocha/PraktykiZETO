import {
  Component,
  EventEmitter,
  Inject,
  OnInit,
  Optional,
  Output,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  FormGroupDirective,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { DialogModalComponent } from '../dialog-modal/dialog-modal.component';
import { DialogModalService } from '../dialog-modal/dialog-modal.service';
import { filter } from 'rxjs';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatNativeDateModule, MatRippleModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatSliderModule } from '@angular/material/slider';
import { MatTooltipModule } from '@angular/material/tooltip';
import { NgbTimepickerModule } from '@ng-bootstrap/ng-bootstrap';
import { CreditCardDirectivesModule } from 'angular-cc-library';
import { AuthService } from './AuthService.service';

type typeOfForm = 'REGISTER' | 'LOGIN';

@Component({
  selector: 'login-modal',
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
    NgbTimepickerModule,
    CreditCardDirectivesModule,
  ],
  templateUrl: './login-modal.component.html',
  styleUrls: ['./login-modal.component.sass'],
})
export class LoginModalComponent implements OnInit {
  showLoader: boolean = false;
  form!: FormGroup;
  typeOfForm: typeOfForm = 'LOGIN';

  hidePassword: boolean = true;
  hideRepeatPassword: boolean = true;

  constructor(
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    public dialogRef: MatDialogRef<LoginModalComponent>,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.form = this.formBuilder.group({
      login: [''],
      password: [''],
    });
  }

  onLogin(): void {
    console.log('onLogin fV ', this.form.value);
    this.authService.login(this.form.value);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  switchForms(): void {
    const currentForm = this.typeOfForm;
    this.typeOfForm = currentForm === 'LOGIN' ? 'REGISTER' : 'LOGIN';
  }
}

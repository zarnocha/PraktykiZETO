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
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  FormGroupDirective,
  FormsModule,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
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

function checkFields(form: FormGroup<any>) {
  for (const controlName in form.controls) {
    if (form.controls[controlName].invalid) {
      console.log(
        'invalid field: ',
        controlName,
        ' value: ',
        form.controls[controlName].value
      );

      if (controlName === 'password') {
        return `Pole \"hasło\" jest błędne.`;
      } else if (controlName === 'login') return `Pole \"login\" jest błędne.`;
      else return `Pole ${controlName} jest błędne.`;
    }
  }
  return '';
}

const checkPasswords: ValidatorFn = (
  group: AbstractControl
): ValidationErrors | null => {
  let pass = group.get('password')!.value;
  let repeatPassword = group.get('repeatPassword')!.value;
  return pass === repeatPassword ? null : { notSame: true };
};

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

  invalidField: string = '';

  hidePassword: boolean = true;
  hideRepeatPassword: boolean = true;

  constructor(
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    public dialogRef: MatDialogRef<LoginModalComponent>,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.form = this.formBuilder.group({
      login: ['', [Validators.minLength(4), Validators.required]],
      password: ['', [Validators.minLength(4), Validators.required]],
    });
  }

  onLogin(): void {
    if (this.form.invalid) {
      this.invalidField = checkFields(this.form);
      return;
    }
    this.invalidField = checkFields(this.form);

    const sub = this.authService.login(this.form.value).subscribe((res) => {
      this.authService.setSession(res);
      this.dialogRef.close();
    });
  }

  onRegister(): void {
    if (this.form.invalid) {
      this.invalidField = checkFields(this.form);
      return;
    }
    this.invalidField = checkFields(this.form);

    this.authService.register(this.form.value).subscribe((res) => {
      console.log('register response', res);
      this.switchForms();
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  switchForms(): void {
    this.invalidField = '';

    const currentForm = this.typeOfForm;
    this.typeOfForm = currentForm === 'LOGIN' ? 'REGISTER' : 'LOGIN';

    if (this.typeOfForm === 'REGISTER') {
      this.form = this.formBuilder.group(
        {
          login: ['', [Validators.required, Validators.minLength(4)]],

          password: ['', [Validators.required, Validators.minLength(4)]],

          repeatPassword: ['', [Validators.required, Validators.minLength(4)]], // validator

          firstName: [
            '',
            [
              Validators.required,
              Validators.pattern(/^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+$/),
              Validators.minLength(4),
            ],
          ],

          lastName: [
            '',
            [
              Validators.required,
              Validators.pattern(/^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+$/),
              Validators.minLength(4),
            ],
          ],

          ccNumber: [
            '',
            [
              Validators.required,
              Validators.pattern(/^\d{4} \d{4} \d{4} \d{4}$/),
            ],
          ],

          ccExpDate: [
            '',
            [Validators.required, Validators.pattern(/^\d{2} \/ \d{2}$/)],
          ],

          cvv: [
            '',
            [
              Validators.required,
              Validators.minLength(3),
              Validators.maxLength(3),
              Validators.pattern(/\d{3}/),
            ],
          ],
        },
        { validators: checkPasswords }
      );
    } else {
      this.form = this.formBuilder.group({
        login: ['', [Validators.minLength(4), Validators.required]],
        password: ['', [Validators.minLength(4), Validators.required]],
      });
    }
  }
}

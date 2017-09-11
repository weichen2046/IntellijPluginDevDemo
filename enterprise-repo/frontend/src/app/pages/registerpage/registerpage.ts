import { Component, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MdButtonModule, MdInputModule } from '@angular/material';

import { FooterModule } from '../../shared/footer';

const EMAIL_REGEX = /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

@Component({
    selector: 'app-register',
    templateUrl: './registerpage.html',
    styleUrls: ['./registerpage.scss'],
})
export class RegisterPageComponent {
    private pwdFormControl = new FormControl('', [
        Validators.required,
    ]);
    private confirmpwdFormControl = new FormControl('', [
        Validators.required,
    ]);
    private formGroup = new FormGroup({
        'email': new FormControl('', [
            Validators.required,
            Validators.pattern(EMAIL_REGEX),
        ]),
        'username': new FormControl('', [
            Validators.required,
        ]),
        'password': this.pwdFormControl,
        'confirmpwd': new FormControl('', [
            Validators.required,
            (c) => {
                return this.pwdFormControl.value === c.value ? null : { 'mismatch': true };
            }
        ]),
    });

    register() {
        // TODO:
        console.log('register called');
    }
}

@NgModule({
    imports: [
        FooterModule,
        MdButtonModule,
        MdInputModule,
        FormsModule,
        ReactiveFormsModule,
        CommonModule,
    ],
    declarations: [RegisterPageComponent],
    exports: [RegisterPageComponent],
})
export class RegisterModule { }

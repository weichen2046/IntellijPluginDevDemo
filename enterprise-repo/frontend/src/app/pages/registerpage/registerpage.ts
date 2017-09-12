import { Component, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MdButtonModule, MdInputModule } from '@angular/material';
import { Router } from '@angular/router';

import { FooterModule } from '../../shared/footer';
import { AuthenticationService } from '../../shared/authentication';

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

    private email: string;
    private username: string;
    private password: string;

    constructor(
        private auth: AuthenticationService,
        private route: Router,
    ) { }

    register() {
        this.auth.register(this.email, this.username, this.password).then(result => {
            if (result) {
                this.auth.login(this.username, this.password).then(resp => {
                    // redirect to home page
                    this.route.navigate(['/']);
                });
            } else {
                console.log('show reigster error');
            }
        });
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

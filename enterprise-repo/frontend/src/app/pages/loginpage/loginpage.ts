import { Component, NgModule, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MdButtonModule, MdInputModule } from '@angular/material';
import { FormsModule, FormControl, Validators, ReactiveFormsModule, FormGroup } from '@angular/forms';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';

import 'rxjs/add/operator/switchMap';

import { FooterModule } from '../../shared/footer';
import { User, AuthenticationModule, AuthenticationService } from '../../shared/authentication';

@Component({
    selector: 'app-login',
    templateUrl: './loginpage.html',
    styleUrls: ['./loginpage.scss',],
})
export class LoginPage implements OnInit {
    private user: User;
    private nextUrl: string;

    private usernameFormControl = new FormControl('', [
        Validators.required
    ]);
    private passwordFormControl = new FormControl('', [
        Validators.required
    ]);

    private formGroup  = new FormGroup({
        'username': this.usernameFormControl,
        'password': this.passwordFormControl,
    })

    constructor(
        private auth: AuthenticationService,
        private route: ActivatedRoute,
        private router: Router
    ) { }

    ngOnInit() {
        this.route.paramMap.subscribe((params: ParamMap) => {
            this.nextUrl = params.get('next');
        });
    }

    login(username: string, password: string) {
        this.auth.login(username, password).then(user => {
            if (!!this.nextUrl) {
                this.router.navigate([this.nextUrl]);
            } else {
                this.router.navigate(['/']);
            }
        });
    }
}

@NgModule({
    imports: [
        AuthenticationModule,
        FooterModule,
        FormsModule,
        ReactiveFormsModule,
        MdButtonModule,
        MdInputModule,
        CommonModule,
    ],
    exports: [LoginPage],
    declarations: [LoginPage],
    providers: [],
})
export class LoginModule { }
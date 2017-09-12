import { Component, NgModule, OnInit, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MdButtonModule, MdInputModule, MdSnackBarModule, MdSnackBar, MD_SNACK_BAR_DATA, MdSnackBarConfig } from '@angular/material';
import { FormsModule, FormControl, Validators, ReactiveFormsModule, FormGroup } from '@angular/forms';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';

import 'rxjs/add/operator/switchMap';

import { FooterModule } from '../../shared/footer';
import { User, AuthenticationModule, AuthenticationService } from '../../shared/authentication';

@Component({
    selector: 'app-login-snackbar',
    templateUrl: './login-snackbar.html',
    styleUrls: ['./login-snackbar.scss'],
})
export class LoginSnackBarComponent {
    constructor(@Inject(MD_SNACK_BAR_DATA) public data: any) {}
}

@Component({
    selector: 'app-login',
    templateUrl: './loginpage.html',
    styleUrls: ['./loginpage.scss',],
})
export class LoginPageComponent implements OnInit {
    private user: User;
    private nextUrl: string;

    private usernameFormControl = new FormControl('', [
        Validators.required
    ]);
    private passwordFormControl = new FormControl('', [
        Validators.required
    ]);

    private formGroup = new FormGroup({
        'username': this.usernameFormControl,
        'password': this.passwordFormControl,
    })

    constructor(
        private auth: AuthenticationService,
        private route: ActivatedRoute,
        private router: Router,
        private snackBar: MdSnackBar,
    ) { }

    ngOnInit() {
        this.route.paramMap.subscribe((params: ParamMap) => {
            this.nextUrl = params.get('next');
        });
    }

    login(username: string, password: string) {
        this.auth.login(username, password)
            .then(user => {
                if (!!this.nextUrl) {
                    this.router.navigate([this.nextUrl]);
                } else {
                    this.router.navigate(['/']);
                }
            })
            .catch(resp => {
                // notify user login failed
                /*
                this.snackBar.openFromComponent(LoginSnackBarComponent, {
                    duration: 2000,
                });
                */
                const rData = JSON.parse(resp._body)
                let config = new MdSnackBarConfig();
                config.duration = 3000;
                config.extraClasses = ['error-hint'];
                this.snackBar.open(rData.message, null, config);
                return null;
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
        MdSnackBarModule,
        CommonModule,
    ],
    exports: [LoginPageComponent],
    declarations: [LoginPageComponent, LoginSnackBarComponent],
    entryComponents: [LoginSnackBarComponent],
    providers: [],
})
export class LoginModule { }

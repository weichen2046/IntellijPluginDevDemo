import { Component, NgModule, OnInit } from '@angular/core';
import { MdButtonModule, MdInputModule } from '@angular/material';
import { FormsModule } from '@angular/forms';
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
    imports: [AuthenticationModule, FooterModule, FormsModule, MdButtonModule, MdInputModule],
    exports: [LoginPage],
    declarations: [LoginPage],
    providers: [],
})
export class LoginModule { }
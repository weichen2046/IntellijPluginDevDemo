import { Component, NgModule } from '@angular/core';
import { MdButtonModule, MdInputModule } from '@angular/material';
import { FormsModule } from '@angular/forms';

import { FooterModule } from '../../shared/footer';

import { User, AuthenticationModule, AuthenticationService } from '../../shared/authentication';

@Component({
    selector: 'app-login',
    templateUrl: './loginpage.html',
    styleUrls: ['./loginpage.scss',],
})
export class LoginPage {
    private user: User;

    constructor(private auth: AuthenticationService) {
    }

    login(username: string, password: string) {
        this.auth.login(username, password);
    }
}

@NgModule({
    imports: [AuthenticationModule, FooterModule, FormsModule, MdButtonModule, MdInputModule],
    exports: [LoginPage],
    declarations: [LoginPage],
    providers: [],
})
export class LoginModule { }
import { Component, NgModule } from '@angular/core';
import { MdButtonModule, MdInputModule } from '@angular/material';

import { FooterModule } from '../../shared/footer';

@Component({
    selector: 'app-login',
    templateUrl: './loginpage.html',
    styleUrls: ['./loginpage.scss',],
})
export class LoginPage { }

@NgModule({
    imports: [FooterModule, MdButtonModule, MdInputModule],
    exports: [LoginPage],
    declarations: [LoginPage],
})
export class LoginModule { }
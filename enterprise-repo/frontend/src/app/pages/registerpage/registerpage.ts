import { Component, NgModule } from '@angular/core';
import { MdButtonModule, MdInputModule } from '@angular/material';

import { FooterModule } from '../../shared/footer';

@Component({
    selector: 'app-register',
    templateUrl: './registerpage.html',
    styleUrls: ['./registerpage.scss'],
})
export class RegisterPage { }

@NgModule({
    imports: [FooterModule, MdButtonModule, MdInputModule],
    declarations: [RegisterPage],
    exports: [RegisterPage],
})
export class RegisterModule { }
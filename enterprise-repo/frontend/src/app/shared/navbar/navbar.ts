import { Component, NgModule } from '@angular/core';
import { MdButtonModule } from '@angular/material';
import { RouterModule } from '@angular/router';

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.html',
    styleUrls: ['./navbar.scss'],
})
export class Navbar { }

@NgModule({
    imports: [MdButtonModule, RouterModule],
    exports: [Navbar],
    declarations: [Navbar],
})
export class NavbarModule { }
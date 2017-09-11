import { CommonModule } from '@angular/common';
import { Component, NgModule } from '@angular/core';
import { MdButtonModule, MdIconModule, MdMenuModule } from '@angular/material';
import { RouterModule, Router } from '@angular/router';

import { AuthenticationModule, AuthenticationService, User } from '../authentication';

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.html',
    styleUrls: ['./navbar.scss'],
})
export class Navbar {
    constructor(
        private auth: AuthenticationService,
        private router: Router
    ) { }

    signOut() {
        this.auth.logout();
        this.router.navigateByUrl('/');
    }
}

@NgModule({
    imports: [AuthenticationModule, CommonModule, MdButtonModule, MdIconModule, MdMenuModule, RouterModule],
    exports: [Navbar],
    declarations: [Navbar],
})
export class NavbarModule { }
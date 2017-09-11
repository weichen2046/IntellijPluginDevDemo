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

    signIn() {
        if (!this.router.isActive('/login', false)) {
            if (this.router.isActive('/register', false)) {
                this.router.navigate(['/login']);
            } else {
                this.router.navigate(['/login', { next: this.router.url }]);
            }
        }
    }

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
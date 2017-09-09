import { Component, NgModule } from '@angular/core';

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.html',
    styleUrls: [ './navbar.css' ],
})
export class Navbar {}

@NgModule({
    exports: [ Navbar ],
    declarations: [ Navbar ],
})
export class NavbarModule {}
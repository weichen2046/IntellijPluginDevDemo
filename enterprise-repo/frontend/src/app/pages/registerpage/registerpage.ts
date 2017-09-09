import { Component, NgModule } from '@angular/core';

import { FooterModule } from '../../shared/footer';

@Component({
    selector: 'app-register',
    templateUrl: './registerpage.html',
    styleUrls: ['./registerpage.css' ],
})
export class RegisterPage {}

@NgModule({
    imports: [ FooterModule ],
    declarations: [ RegisterPage ],
    exports: [ RegisterPage ],
})
export class RegisterPageModule {}
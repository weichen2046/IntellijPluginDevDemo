import { Component, NgModule } from '@angular/core';

import { FooterModule } from '../../shared/footer';

@Component({
    selector: 'app-homepage',
    templateUrl: './homepage.html',
    styleUrls: [ './homepage.css' ]
})
export class HomePage {}

@NgModule({
    imports: [ FooterModule ],
    declarations: [HomePage],
    exports: [HomePage],
})
export class HomeModule {}
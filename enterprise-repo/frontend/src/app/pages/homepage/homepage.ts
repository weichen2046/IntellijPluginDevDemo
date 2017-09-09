import { Component, NgModule } from '@angular/core';

import { FooterModule } from '../../shared/footer';

@Component({
    selector: 'app-homepage',
    templateUrl: './homepage.html',
    styleUrls: [ './homepage.css' ]
})
export class Homepage {}

@NgModule({
    imports: [ FooterModule ],
    declarations: [Homepage],
    exports: [Homepage],
})
export class HomepageModule {}
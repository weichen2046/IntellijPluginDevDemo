import { Component, NgModule } from '@angular/core';

@Component({
    selector: 'app-footer',
    templateUrl: './footer.html',
    styleUrls: [ './footer.css' ],
})
export class Footer {}

@NgModule({
    exports: [ Footer ],
    declarations: [ Footer ],
})
export class FooterModule {}
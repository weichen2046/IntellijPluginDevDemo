import { NgModule } from '@angular/core';
import { HttpModule, XSRFStrategy, CookieXSRFStrategy } from '@angular/http';

import { AuthenticationService } from './authentication.service';

export let xsrfStrategyFactory = () => {
    return new CookieXSRFStrategy('csrftoken', 'X-CSRFToken');
};

export let xsrfStrategyProvider = {
    provide: XSRFStrategy,
    useFactory: xsrfStrategyFactory,
};

@NgModule({
    imports: [HttpModule],
    providers: [
        xsrfStrategyProvider,
        AuthenticationService,
    ],
    exports: [],
})
export class AuthenticationModule { }
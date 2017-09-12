import { HomePage } from './pages/homepage';
import { LoginPageComponent } from './pages/loginpage';
import { RegisterPageComponent } from './pages/registerpage';

import { Routes } from '@angular/router';

export const APP_ROUTES: Routes = [
    { path: '', component: HomePage, pathMatch: 'full' },
    { path: 'login', component: LoginPageComponent, },
    { path: 'register', component: RegisterPageComponent, },
]
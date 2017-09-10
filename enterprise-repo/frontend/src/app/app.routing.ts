import { HomePage } from './pages/homepage';
import { LoginPage } from './pages/loginpage';
import { RegisterPage } from './pages/registerpage';

import { Routes } from '@angular/router';

export const APP_ROUTES: Routes = [
    { path: '', component: HomePage, pathMatch: 'full' },
    { path: 'login', component: LoginPage, },
    { path: 'register', component: RegisterPage, },
]
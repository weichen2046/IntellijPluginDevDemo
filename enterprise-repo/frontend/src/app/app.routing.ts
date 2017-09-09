import { Homepage } from './pages/homepage';
import { RegisterPage } from './pages/registerpage';

import { Routes } from '@angular/router';

export const APP_ROUTES: Routes = [
    { path: '', component: Homepage, pathMatch: 'full' },
    { path: 'register', component: RegisterPage, },
]
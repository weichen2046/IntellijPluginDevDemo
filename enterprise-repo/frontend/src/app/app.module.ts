import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { APP_ROUTES } from './app.routing';
import { AppComponent } from './app.component';
import { HomepageModule } from './pages/homepage';
import { NavbarModule } from './shared/navbar';
import { RegisterPageModule } from './pages/registerpage';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(APP_ROUTES),
    HomepageModule,
    NavbarModule,
    RegisterPageModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

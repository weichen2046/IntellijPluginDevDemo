import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { APP_ROUTES } from './app.routing';
import { AppComponent } from './app.component';
import { HomeModule } from './pages/homepage';
import { LoginModule } from './pages/loginpage';
import { NavbarModule } from './shared/navbar';
import { RegisterModule } from './pages/registerpage';
import { UploadPluginModule } from './pages/uploadpluginpage';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(APP_ROUTES),
    HomeModule,
    LoginModule,
    NavbarModule,
    RegisterModule,
    UploadPluginModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

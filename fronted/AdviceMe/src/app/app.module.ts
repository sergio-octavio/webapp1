import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { routing } from './app.routing';

// Films
import { FilmListAdviceMeComponent } from './components/films/filmListAdviceMe.component';
import { FilmUnregisteredComponent } from './components/films/filmUnregistered.component';

// User
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/login/register.component';

@NgModule({
  declarations: [AppComponent, FilmListAdviceMeComponent, FilmUnregisteredComponent,
    LoginComponent, RegisterComponent],
  imports: [BrowserModule, FormsModule, HttpClientModule, routing],
  providers: [],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }

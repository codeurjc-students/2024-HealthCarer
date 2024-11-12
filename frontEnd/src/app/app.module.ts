import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { MedicationDetailComponent } from './components/medications/medication-detail.component';
import { MedicationListComponent } from './components/medications/medication-list.component';
import { MedicationFormComponent } from './components/medications/medication-form.component';
import { routing } from './app.routing';
import { LoginComponent } from './components/login/login.component';
import {RegisterComponent} from "./components/login/register.component";

@NgModule({
  declarations: [AppComponent, MedicationDetailComponent, MedicationListComponent, MedicationFormComponent, LoginComponent, RegisterComponent],
  imports: [BrowserModule, FormsModule, HttpClientModule, routing],
  bootstrap: [AppComponent]
})
export class AppModule { }

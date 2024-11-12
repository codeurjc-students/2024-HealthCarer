import { Routes, RouterModule } from '@angular/router';

import { MedicationListComponent } from './components/medications/medication-list.component';
import { MedicationDetailComponent } from './components/medications/medication-detail.component';
import { MedicationFormComponent } from './components/medications/medication-form.component';
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/login/register.component";

const appRoutes: Routes = [
    { path: 'medications', component: MedicationListComponent },
    { path: 'medications/new', component: MedicationFormComponent },
    { path: 'medications/:id', component: MedicationDetailComponent },
    { path: 'medications/edit/:id', component: MedicationFormComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: '', redirectTo: 'login', pathMatch: 'full' }
]

export const routing = RouterModule.forRoot(appRoutes);

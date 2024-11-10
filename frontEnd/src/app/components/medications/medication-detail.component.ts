import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { MedicationsService } from '../../services/medications.service';
import { Medication } from '../../models/medication.model';
import { LoginService } from 'src/app/services/login.service';

@Component({
    templateUrl: './medication-detail.component.html'
})
export class MedicationDetailComponent {

    medication: Medication;

    constructor(private router: Router, activatedRoute: ActivatedRoute, public service: MedicationsService,
        public loginService: LoginService) {

        const id = activatedRoute.snapshot.params['id'];
        service.getMedication(id).subscribe(
            medication => this.medication = medication,
            error => console.error(error)
        );
    }

    removeMedication() {
        const okResponse = window.confirm('Do you want to remove this medication?');
        if (okResponse) {
            this.service.deleteMedication(this.medication).subscribe(
                _ => this.router.navigate(['/medications']),
                error => console.error(error)
            );
        }
    }

    editMedication() {
        this.router.navigate(['/medications/edit', this.medication.id]);
    }

    gotoMedications() {
        this.router.navigate(['/medications']);
    }

    medicationImage(){
        return this.medication.image? '/api/medications/'+this.medication.id+'/image' : '/assets/images/no_image.png';
    }
}

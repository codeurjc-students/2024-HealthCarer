import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { Medication } from '../../models/medication.model';
import { MedicationsService } from '../../services/medications.service';


@Component({
  templateUrl: './medication-list.component.html'
})
export class MedicationListComponent implements OnInit {

  medications: Medication[];

  constructor(private router: Router, private service: MedicationsService, public loginService: LoginService) { }

  ngOnInit() {
    this.service.getMedications().subscribe(
      medications => this.medications = medications,
      error => console.log(error)
    );
  }

  newMedication() {
    this.router.navigate(['/medications/new']);
  }
}

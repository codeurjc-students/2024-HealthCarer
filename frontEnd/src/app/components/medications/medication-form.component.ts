import { Component, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { MedicationsService } from '../../services/medications.service';
import { Medication } from '../../models/medication.model';

@Component({
  templateUrl: './medication-form.component.html'
})
export class MedicationFormComponent {

  newMedication: boolean;
  medication: Medication;

  @ViewChild("file")
  file: any;

  removeImage:boolean;

  constructor(
    private router: Router,
    activatedRoute: ActivatedRoute,
    private service: MedicationsService) {

    const id = activatedRoute.snapshot.params['id'];
    if (id) {
      service.getMedication(id).subscribe(
        medication => this.medication = medication,
        error => console.error(error)
      );
      this.newMedication = false;
    } else {
      this.medication = {name: '', instructions: '', image: false, dose: 0, stock: 0, };
      this.newMedication = true;
    }
  }

  cancel() {
    window.history.back();
  }

  save() {
    if(this.medication.image && this.removeImage){
      this.medication.image = false;
    }
    this.service.addMedication(this.medication).subscribe(
      (medication: Medication) => this.uploadImage(medication),
      error => alert('Error creating new medication: ' + error)
    );
  }

  uploadImage(medication: Medication): void {

    const image = this.file.nativeElement.files[0];
    if (image) {
      let formData = new FormData();
      formData.append("imageFile", image);
      this.service.setMedicationImage(medication, formData).subscribe(
        _ => this.afterUploadImage(medication),
        error => alert('Error uploading medication image: ' + error)
      );
    } else if(this.removeImage){
      this.service.deleteMedicationImage(medication).subscribe(
        _ => this.afterUploadImage(medication),
        error => alert('Error deleting medication image: ' + error)
      );
    } else {
      this.afterUploadImage(medication);
    }
  }

  private afterUploadImage(medication: Medication){
    this.router.navigate(['/medications/', medication.id]);
  }

  medicationImage() {
    return this.medication.image ? '/api/medications/' + this.medication.id + '/image' : '/assets/images/no_image.png';
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.medication.image = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }

}



import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Medication } from '../models/medication.model';

const BASE_URL = '/api/medications/';

@Injectable({ providedIn: 'root' })
export class MedicationsService {

	constructor(private httpClient: HttpClient) { }

	getMedications(): Observable<Medication[]> {
		return this.httpClient.get(BASE_URL).pipe(
			catchError(error => this.handleError(error))
		) as Observable<Medication[]>;
	}

	getMedication(id: number | string): Observable<Medication> {
		return this.httpClient.get(BASE_URL + id).pipe(
			catchError(error => this.handleError(error))
		) as Observable<Medication>;
	}

	addMedication(medication: Medication) {

		if (!medication.id) {
			return this.httpClient.post(BASE_URL, medication)
				.pipe(
					catchError(error => this.handleError(error))
				);
		} else {
			return this.httpClient.put(BASE_URL + medication.id, medication).pipe(
				catchError(error => this.handleError(error))
			);
		}
	}

	setMedicationImage(medication: Medication, formData: FormData) {
		return this.httpClient.post(BASE_URL + medication.id + '/image', formData)
			.pipe(
				catchError(error => this.handleError(error))
			);
	}

	deleteMedicationImage(medication: Medication) {
		return this.httpClient.delete(BASE_URL + medication.id + '/image')
			.pipe(
				catchError(error => this.handleError(error))
			);
	}

	deleteMedication(medication: Medication) {
		return this.httpClient.delete(BASE_URL + medication.id).pipe(
			catchError(error => this.handleError(error))
		);
	}

	updateMedication(medication: Medication) {
		return this.httpClient.put(BASE_URL + medication.id, medication).pipe(
			catchError(error => this.handleError(error))
		);
	}

	private handleError(error: any) {
		console.log("ERROR:");
		console.error(error);
		return throwError("Server error (" + error.status + "): " + error.text())
	}
}
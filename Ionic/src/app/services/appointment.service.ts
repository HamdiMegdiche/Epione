import { Appointment } from '../models/appointment';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { map, filter, switchMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  UrlAPI = 'http://localhost:8089/epione-web/appointment/';
  constructor(private http: HttpClient) { }


  getAllAppointments(): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(this.UrlAPI)
    .pipe(map((response: any) => response));
  }

  cancelAppointment(id: string): Observable<string> {
    return this.http.put(this.UrlAPI + id, {}, {})
    .pipe(map((response: any) => response));
  }

}

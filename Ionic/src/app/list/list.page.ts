import { Router } from '@angular/router';
import { Globals } from './../models/globals';
import { Appointment } from './../models/appointment';
import { AppointmentService } from './../services/appointment.service';
import { Component, OnInit } from '@angular/core';
import { AlertController } from '@ionic/angular';

@Component({
  selector: 'app-list',
  templateUrl: 'list.page.html',
  styleUrls: ['list.page.scss']
})
export class ListPage implements OnInit {
  public appointments: Appointment[];

  constructor(public appointmentService: AppointmentService, public router: Router,
    public alertController: AlertController,
    public globals: Globals) {
  }

  ngOnInit() {
    if (this.globals.currentUser !== null) {
      this.appointmentService.getAllAppointments().subscribe(data => this.globals.appointments = data);
    } else {
       // Navigate to Home
       this.router.navigate(['/login']);
    }
  }
  // add back when alpha.4 is out
  // navigate(item) {
  //   this.router.navigate(['/list', JSON.stringify(item)]);
  // }

  async cancelAppointment(a: Appointment) {
    const alert = await this.alertController.create({
      header: 'Confirm !',
      message: 'Are you sure, you want to <strong>cancel</strong> this appointment ?',
      buttons: [
        {
          text: 'Okay',
          cssClass: 'primary',
          handler: () => {
            console.log('Confirm Cancel appointment');
            this.globals.appointments.sort((a1, a2) => this.toMinutes(a1.startTime) - this.toMinutes(a2.startTime));
            const index = this.globals.appointments.indexOf(a, 0);
            console.log('indeeex : ' + index);
            if (index > -1 && index + 1 <= this.globals.appointments.length) {
              this.globals.appToUpdate = a;
              this.globals.sendNotif( this.globals.appointments[index + 1].patient.id.toString(),
                                     this.globals.appointments[index + 1].date.toString() + ' ' + a.startTime.toString());
                                     console.log('indeeex if : ' + index);
            }
            this.globals.appointments.splice(index, 1);

          }
        },
        {
          text: 'No',
          role: 'cancel',
          cssClass: 'secondary'
        }
      ]
    });

    await alert.present();
  }

  toMinutes(time: string): number {
    return Number(time.substr(0, 2)) * 60 + Number(time.substr(0, 2)) ;
  }
}

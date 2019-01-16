import { Appointment } from './appointment';
import { AlertController } from '@ionic/angular';
import { Injectable } from '@angular/core';
import { User } from './user';
import {hubConnection} from 'signalr-no-jquery';

const connection = hubConnection('http://localhost:41120/signalr/hubs');
const hubProxy = connection.createHubProxy('ChatHub');

@Injectable()
export class Globals {
  currentUser: User;
 public appointments: Appointment[];
  appToUpdate: Appointment;

  // constructor( public alertController: AlertController) {
  // }

  public InitSignalRWithId(id) {
    const self = this;
    hubProxy.on('Message', async function (message) {

      const alertController: AlertController = new AlertController();
      const alert = await alertController.create({
        header: 'Confirm !',
        message: 'Would you like to  <strong>move</strong> your appointment to <strong>' + message + '</strong>',
        buttons: [
          {
            text: 'Yes',
            cssClass: 'primary',
            handler: () => {
              console.log('Confirm Okay');
              //for(i=0;i< appoi)
              self.appointments.find(a => a.patient.id === id).startTime = message;
             // self.appointments.find(a => a.patient.id === id).endTime = self.appToUpdate.endTime;

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
    });
    connection.qs = 'userId=' + id;
    // connect
    console.log(connection);
    connection.start({ transport: ['webSockets', 'longPolling'] })
      .done(function () {
        //  console.log(hubProxy);
        //  console.log(connection);
        console.log('Now connected, connection ID=' + connection.id);
      })
      .fail(function () { console.log('Could not connect'); });
  }

  public sendNotif(id: string, msg: string) {
    hubProxy.invoke('Message', msg, id).done((directResponse) => {
      console.log('direct-response-from-server', directResponse);
    }).fail((response) => {
      console.warn('Something went wrong when calling server, it might not be up and running?' + response);
    });
  }
}

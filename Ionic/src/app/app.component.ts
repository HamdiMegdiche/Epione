import { Router } from '@angular/router';
import { Globals } from './models/globals';
import { Component } from '@angular/core';

import { Platform, AlertController } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html'
})
export class AppComponent {
  public disconnectedPages = [
    {
      title: 'Home',
      url: '/home',
      icon: 'home'
    },
    {
      title: 'Login',
      url: '/login',
      icon: 'log-in'
    }
  ];
  public connectedPages = [
    {
      title: 'Home',
      url: '/home',
      icon: 'home'
    },
    {
      title: 'Appointments',
      url: '/appointments',
      icon: 'bookmarks'
    }
  ];

  constructor(public globals: Globals,
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar, public alertController: AlertController, public router: Router,
  ) {
    this.initializeApp();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();
    });
  }

async logout() {
    const alert = await this.alertController.create({
      header: 'Confirm !',
      message: 'Are you sure you want to  <strong>log out</strong> from this account !!!',
      buttons: [
        {
          text: 'Yes',
          cssClass: 'primary',
          handler: () => {
            this.globals.currentUser = null;
            console.log('Confirm Okay');
                   // Navigate to Home
          this.router.navigate(['/login']);

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
  
}

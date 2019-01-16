import { User } from './../models/user';
import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';
import {FormBuilder, FormGroup, Validators, AbstractControl, FormControl} from '@angular/forms';
import { ToastController } from '@ionic/angular';
import { Globals } from '../models/globals';
import { hubConnection } from 'signalr-no-jquery';



@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss']
})
export class LoginPage implements OnInit {
  errorMsg = '';
  loginForm: FormGroup;


  constructor( public globals: Globals,
    private userService: UserService,
    private router: Router,
    public formbuilder: FormBuilder,
    public toastController: ToastController
  ) {}

  ngOnInit() {
    this.loginForm = this.formbuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  /**
   * SignIn
   */
  public SignIn() {
    this.errorMsg = '';

    this.userService.AutheticateMe(this.email.value, this.password.value)
    .subscribe((token: string) => {
       this.globals.currentUser = new User();
       this.globals.currentUser.token = token;
       console.log('token form subscribe : ' +  this.globals.currentUser.token);

       if ( token.length < 2) {
        this.errorMsg = 'Email or/and password is/are invalid !';

       } else {
          this.userService.GetUserByEmail(this.email.value).subscribe(
            data => {
              this.globals.currentUser = data;
              // Connect to signalR
                this.globals.InitSignalRWithId(this.globals.currentUser.id);
              // End SignalR

              console.log( this.globals.currentUser);
              if ( this.globals.currentUser !== null) {
                this.presentToast(`Hello, ${ this.globals.currentUser.firstName} ${ this.globals.currentUser.lastName}`);
                // Navigate to Home
                this.router.navigate(['/home']);
              } else {
                this.errorMsg = 'Email or/and password is/are invalid !';
              }
            },
            error => (this.errorMsg = error)
          );
        }
      },
      error => this.errorMsg = error);

  }


  get email() {
    return this.loginForm.get('email');
  }

  get password() {
    return this.loginForm.get('password');
  }

  async presentToast(msg: string) {
    const toast = await this.toastController.create({
      message: msg,
      duration: 3000,
      position: 'top',
      showCloseButton: true
    });
    toast.present();
  }

}

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { map, filter, switchMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  UrlAPI = 'http://localhost:8089/epione-web/user/';
  constructor(private http: HttpClient) { }


   AutheticateMe(email: string , password: string): Observable<string> {
    const options = {responseType: 'text' as 'json'};
    return this.http.post<string>(this.UrlAPI + 'authenticationEmail?' + 'email=' + email + '&password=' + password, {},
     options)
    .pipe(map((response: any) => response));
  }

  GetUserByEmail(email: string): Observable<any> {
    return this.http.get(this.UrlAPI + 'ByEmailOrUsername/' + email)
    .pipe(map((response: any) => response));
  }

  public async getuser(email: string , password: string): Promise<string> {
    let token = '';
    const options = {responseType: 'text' as 'json'};

    await this.http.post<string>(this.UrlAPI + 'authenticationEmail?' + 'email=' + email + '&password=' + password, {},
    options).toPromise().then((data: string) => token = data);
    return token;
}
}

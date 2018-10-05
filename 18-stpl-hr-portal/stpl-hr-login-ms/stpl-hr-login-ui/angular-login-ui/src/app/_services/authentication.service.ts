import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { User } from '../_models/index';
import 'rxjs/add/operator/map'

@Injectable()
export class AuthenticationService {
    constructor(private http: HttpClient) { }

    /*login(username: string, password: string) {
        return this.http.post<any>('/api/authenticate', { username: username, password: password })
            .map(user => {
                // login successful if there's a jwt token in the response
                if (user && user.token) {
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify(user));
                }

                return user;
            });
    }*/
    login(username: string, password: string) {
      let userModel = new User();
      let errorMsg = '';
      return this.http.post<any>('http://localhost:8989/loginMVC/loginMs/checkUserCredentials', JSON.stringify({userName: username, password: password}))
        .map(responseFromWs => {
          if (responseFromWs.status === 'Success') {
            userModel.username = responseFromWs.userName;
            userModel.firstName = responseFromWs.firstName;
            userModel.lastName = responseFromWs.lastName;
            userModel.id=responseFromWs.aggregateId;
              if(userModel) {
              console.log(JSON.stringify(userModel));
              localStorage.setItem('currentUser', JSON.stringify(userModel));
            }
            return userModel;
          }else{
          alert(responseFromWs.failureReason);
          }
        });
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
    }
}
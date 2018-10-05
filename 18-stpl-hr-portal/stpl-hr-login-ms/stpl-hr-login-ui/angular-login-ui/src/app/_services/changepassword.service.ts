import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'

import { ChangePassword } from '../_models/index';

@Injectable()
export class ChangePasswordService {
    constructor(private http: HttpClient) { }

    /*login(username: string, password: string) {
        return this.http.post('/api/authenticate', JSON.stringify({ username: username, password: password }))
            .map((response: Response) => {
                // login successful if there's a jwt token in the response
                let user = response.json();
                if (user && user.token) {
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify(user));
                }

                return user;
            });
    }*/
    changePassword(changePassword: ChangePassword) {
        let currentUser = JSON.parse(localStorage.getItem('currentUser'));
        console.log('--------------------' + changePassword.newPassword);
        console.log('Current User Aggregate Id--' + currentUser.id);
        if (changePassword.newPassword === changePassword.confirmNewPassword) {
            return this.http.post<any>('http://localhost:8989/loginMVC/loginMs/updatePassword', JSON.stringify({ userName: currentUser.username, oldPassword: changePassword.oldPassword, newPassword: changePassword.newPassword,aggregateId:currentUser.id }))
                .map(responseFromWs => {

                    if (responseFromWs.status === 'Success') {
                        alert('Password change successfully.');
                        return;
                    } else {
                        alert(responseFromWs.failureReason);
                    }
                });
        } else {
            alert('New Password and Confirm password should be same.');
        }

    }
}
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'

import { UserDetail } from '../_models/index';

@Injectable()
export class UserRegistrationService {
    constructor(private http: HttpClient) { }

   
    registerUser(userDetail: UserDetail) {
        console.log('User Detailssssssss--' + JSON.stringify(userDetail));
            return this.http.post<any>('http://localhost:8989/loginMVC/loginMs/createUser', JSON.stringify(userDetail))
                .map(responseFromWs => {

                    if (responseFromWs.status === 'Success') {
                        alert('User created .');
                        return;
                    } else {
                        alert('User didnt Create');
                    }
                });
       

    }
}
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders,HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';

import { UserDetail } from '../_models/index';

@Injectable()
export class UserRegistrationService {
    constructor(private http: HttpClient) { }

   
    registerUser(userDetail: UserDetail):Observable<any> {
        console.log('User Detailssssssss--' + JSON.stringify(userDetail));
            return this.http.post<any>('http://localhost:8990/OnboardMVC/OnboardMs/onboardCandidate', JSON.stringify(userDetail))
                .map(responseFromWs => {

                    if (responseFromWs.status === 'Success') {
                        alert('Associate Onboarded');
//                        Observable.of(responseFromWs);
                        return;
                    } else {
                        alert(responseFromWs.failureReason);
//                        return Observable.throw('User didnt Create');
                    }
                });
       

    }
}
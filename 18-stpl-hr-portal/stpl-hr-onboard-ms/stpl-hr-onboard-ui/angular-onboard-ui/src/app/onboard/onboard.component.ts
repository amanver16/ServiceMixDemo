import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';

import { UserRegistrationService,ReturnsJsonArrayService,AlertService } from '../_services/index';

@Component({
    moduleId: module.id.toString(),
    templateUrl: 'onboard.component.html'
})

export class OnboardAssociateComponent {
    model: any = {};
    loading = false;
    data: Observable<Array<any>>;

    constructor(
        private router: Router,
        private userRegService: UserRegistrationService,private alertService :AlertService,private jsonAssociateService: ReturnsJsonArrayService
       ) { 
        this.data = jsonAssociateService.getAssociates();
         console.log("AppComponent.data:" + this.data);
    }

    onboardAssociate() {
        this.loading = true;
        this.userRegService.registerUser(this.model)
            .subscribe(
                data => {
                    console.log("Dataaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//                    this.router.navigate(['']);
//                    window.location.href = 'http://localhost:8181/stpl-hr-login-ui/';
                },
                error => {
                    console.log('Errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr');
                     this.alertService.error(error);
                    this.loading = false;
                });
    }
}

import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AlertService, ChangePasswordService } from '../_services/index';

@Component({
    moduleId: module.id,
    templateUrl: 'changepassword.component.html'
})

export class ChangePasswordComponent {
    model: any = {};
    loading = false;

    constructor(
        private router: Router,
        private changePasswordService: ChangePasswordService,
        private alertService: AlertService) { }

    changePassword() {
        this.loading = true;
        this.changePasswordService.changePassword(this.model)
            .subscribe(
                data => {
//                    this.alertService.success('Password Changed Successfully', true);
                    this.router.navigate(['/**']);
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                });
    }
}

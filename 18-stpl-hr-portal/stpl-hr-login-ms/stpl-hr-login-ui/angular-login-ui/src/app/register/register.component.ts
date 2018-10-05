import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AlertService, UserRegistrationService } from '../_services/index';

@Component({
    moduleId: module.id.toString(),
    templateUrl: 'register.component.html'
})

export class RegisterComponent {
    model: any = {};
    loading = false;

    constructor(
        private router: Router,
        private userRegService: UserRegistrationService,
        private alertService: AlertService) { }

    register() {
        this.loading = true;
        this.userRegService.registerUser(this.model)
            .subscribe(
                data => {
                    this.alertService.success('Registration successful', true);
                    this.router.navigate(['/login']);
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                });
    }
}

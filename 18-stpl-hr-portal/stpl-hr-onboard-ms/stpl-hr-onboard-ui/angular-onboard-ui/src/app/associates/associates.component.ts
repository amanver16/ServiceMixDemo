import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';

import { ReturnsJsonArrayService } from '../_services/index';

@Component({
    moduleId: module.id.toString(),
    templateUrl: 'associates.component.html'
})

export class AssociatesComponent {
    model: any = {};
    loading = false;
    data: Observable<Array<any>>;

    constructor(
        private router: Router,
        private jsonAssociateService: ReturnsJsonArrayService
       ) { 
        this.data = jsonAssociateService.getAssociates();
         console.log("AppComponent.data:" + this.data);
    }  
}

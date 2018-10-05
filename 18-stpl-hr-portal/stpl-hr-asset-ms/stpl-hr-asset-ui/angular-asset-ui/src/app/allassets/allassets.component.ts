import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';

import { ReturnsJsonArrayService } from '../_services/index';

@Component({
    moduleId: module.id.toString(),
    templateUrl: 'allassets.component.html'
})

export class AssetListComponent {
    model: any = {};
    loading = false;
    data: Observable<Array<any>>;

    constructor(
        private router: Router,
        private jsonAssetsService: ReturnsJsonArrayService
       ) { 
        this.data = jsonAssetsService.getAssets();
         console.log("AppComponent.data:" + this.data);
    }  
}

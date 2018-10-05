import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';

import { AssetCreationService,ReturnsJsonArrayService,AlertService } from '../_services/index';

@Component({
    moduleId: module.id.toString(),
    templateUrl: 'createasset.component.html'
})

export class AssetCreationComponent {
    model: any = {};
    loading = false;
    data: Observable<Array<any>>;

    constructor(
        private router: Router,
        private assetService: AssetCreationService,private alertService :AlertService,private jsonAssociateService: ReturnsJsonArrayService
       ) { 
        this.data = jsonAssociateService.getAssets();
         console.log("AppComponent.data:" + this.data);
    }

    createAssets() {
        this.loading = true;
        this.assetService.createAsset(this.model)
        .subscribe(
                data => {
                    console.log("Dataaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                },
                error => {
                    console.log('Errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr');
                    this.loading = false;
                });
            
    }
}

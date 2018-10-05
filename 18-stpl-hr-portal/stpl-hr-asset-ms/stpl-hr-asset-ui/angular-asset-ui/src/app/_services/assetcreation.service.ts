import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders,HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';

import { AssetDetails } from '../_models/index';


@Injectable()
export class AssetCreationService {
    constructor(private http: HttpClient) { }

   
    createAsset(assetDetails: AssetDetails):Observable<any> {
        console.log('Asset Details====================='+JSON.stringify(assetDetails));
         return this.http.post<any>('http://localhost:8991/assetMVC/AssetMs/addAsset', JSON.stringify({ assetId: assetDetails.assetId, assetType: assetDetails.assetType, specification: assetDetails.specification}))
                .map(responseFromWs => {
                    if (responseFromWs.status === 'Success') {
                        alert('Asset Added');
//                        Observable.of(responseFromWs);
                        return;
                    } else {
                        alert(responseFromWs.failureReason);
//                        return Observable.throw('User didnt Create');
                    }
                });
    }
}
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

@Injectable()
export class ReturnsJsonArrayService {

  constructor(private http: HttpClient) {}

  getAssets(): Observable<any> {
        return this.http.get('http://localhost:8991/assetMVC/AssetMs/getAllAssets')
            .catch((error:any) => Observable.throw(error || 'Server error'));
  }

}

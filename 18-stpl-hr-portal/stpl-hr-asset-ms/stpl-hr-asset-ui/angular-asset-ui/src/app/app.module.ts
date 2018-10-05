import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule }    from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AlertComponent } from './_directives/index';
import { AssetCreationComponent } from './createasset/index';
import { HomeComponent } from './home/index';
import { AssetListComponent } from './allassets/index';
import { routing }        from './app.routing';
import { AlertService,AssetCreationService,ReturnsJsonArrayService } from './_services/index';

import { AppComponent } from './app.component';


@NgModule({
  declarations: [
    AppComponent,
      AlertComponent,
      HomeComponent,
      AssetListComponent,
      AssetCreationComponent
  ],
  imports: [
    BrowserModule,
      FormsModule,
      HttpClientModule,
      routing
  ],
  providers: [AlertService,AssetCreationService,ReturnsJsonArrayService],
  bootstrap: [AppComponent]
})
export class AppModule { }

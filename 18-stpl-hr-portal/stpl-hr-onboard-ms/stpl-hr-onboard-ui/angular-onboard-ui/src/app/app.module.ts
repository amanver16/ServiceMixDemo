import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule }    from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AlertComponent } from './_directives/index';
import { routing }        from './app.routing';
import { AlertService,UserRegistrationService,ReturnsJsonArrayService } from './_services/index';
import { OnboardAssociateComponent } from './onboard/index';
import { HomeComponent } from './home/index';
import { AssociatesComponent } from './associates/index';

import { AppComponent } from './app.component';


@NgModule({
  declarations: [
    AppComponent,
      AlertComponent,
      OnboardAssociateComponent,
      HomeComponent,
      AssociatesComponent
  ],
  imports: [
    BrowserModule,
       FormsModule,
        HttpClientModule,
      routing
  ],
  providers: [
  AlertService,UserRegistrationService,ReturnsJsonArrayService
      ],
  bootstrap: [AppComponent]
})
export class AppModule { }

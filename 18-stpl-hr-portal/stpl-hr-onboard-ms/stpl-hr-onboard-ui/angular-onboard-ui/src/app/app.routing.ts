import { Routes, RouterModule } from '@angular/router';
import { OnboardAssociateComponent } from './onboard/index';
import { AssociatesComponent } from './associates/index';
import { HomeComponent } from './home/index';

const appRoutes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'initiateOnboard', component: OnboardAssociateComponent },
    { path: 'listAllAssociate', component: AssociatesComponent }
];

export const routing = RouterModule.forRoot(appRoutes);
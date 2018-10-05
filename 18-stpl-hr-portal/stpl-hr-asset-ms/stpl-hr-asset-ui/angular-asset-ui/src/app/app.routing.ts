import { Routes, RouterModule } from '@angular/router';
import { AssetCreationComponent } from './createasset/index';
import { AssetListComponent } from './allassets/index';
import { HomeComponent } from './home/index';

const appRoutes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'addNewAsset', component: AssetCreationComponent },
    { path: 'listAllAsset', component: AssetListComponent }
];

export const routing = RouterModule.forRoot(appRoutes);
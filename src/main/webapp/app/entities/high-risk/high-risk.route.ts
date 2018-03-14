import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';

import {HighRiskComponent} from './high-risk.component';
import {HighRiskDetailComponent} from './high-risk-detail.component';
import {HighRiskPopupComponent} from './high-risk-dialog.component';
import {HighRiskDeletePopupComponent} from './high-risk-delete-dialog.component';

export const highRiskRoute: Routes = [
    {
        path: 'high-risk',
        component: HighRiskComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.highRisk.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'high-risk/:id',
        component: HighRiskDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.highRisk.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const highRiskPopupRoute: Routes = [
    {
        path: 'high-risk-new',
        component: HighRiskPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.highRisk.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'high-risk/:id/edit',
        component: HighRiskPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.highRisk.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'high-risk/:id/delete',
        component: HighRiskDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.highRisk.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

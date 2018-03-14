import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';

import {PossibilitiesToImproveRiskComponent} from './possibilities-to-improve-risk.component';
import {PossibilitiesToImproveRiskDetailComponent} from './possibilities-to-improve-risk-detail.component';
import {PossibilitiesToImproveRiskPopupComponent} from './possibilities-to-improve-risk-dialog.component';
import {PossibilitiesToImproveRiskDeletePopupComponent} from './possibilities-to-improve-risk-delete-dialog.component';

export const possibilitiesToImproveRiskRoute: Routes = [
    {
        path: 'possibilities-to-improve-risk',
        component: PossibilitiesToImproveRiskComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.possibilitiesToImproveRisk.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'possibilities-to-improve-risk/:id',
        component: PossibilitiesToImproveRiskDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.possibilitiesToImproveRisk.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const possibilitiesToImproveRiskPopupRoute: Routes = [
    {
        path: 'possibilities-to-improve-risk-new',
        component: PossibilitiesToImproveRiskPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.possibilitiesToImproveRisk.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'possibilities-to-improve-risk/:id/edit',
        component: PossibilitiesToImproveRiskPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.possibilitiesToImproveRisk.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'possibilities-to-improve-risk/:id/delete',
        component: PossibilitiesToImproveRiskDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.possibilitiesToImproveRisk.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

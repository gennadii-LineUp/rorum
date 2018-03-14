import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';

import {DecisionForRiskComponent} from './decision-for-risk.component';
import {DecisionForRiskDetailComponent} from './decision-for-risk-detail.component';
import {DecisionForRiskPopupComponent} from './decision-for-risk-dialog.component';
import {DecisionForRiskDeletePopupComponent} from './decision-for-risk-delete-dialog.component';

export const decisionForRiskRoute: Routes = [
    {
        path: 'decision-for-risk',
        component: DecisionForRiskComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.decisionForRisk.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'decision-for-risk/:id',
        component: DecisionForRiskDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.decisionForRisk.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const decisionForRiskPopupRoute: Routes = [
    {
        path: 'decision-for-risk-new',
        component: DecisionForRiskPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.decisionForRisk.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'decision-for-risk/:id/edit',
        component: DecisionForRiskPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.decisionForRisk.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'decision-for-risk/:id/delete',
        component: DecisionForRiskDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.decisionForRisk.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

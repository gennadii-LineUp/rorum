import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {JhiPaginationUtil} from 'ng-jhipster';
import { ReportingComponent } from './reporting.component';
import { ReportingOrderComponent } from './reporting-order.component';

export const reportingRoute: Routes = [
    {
        path: 'reporting',
        component: ReportingComponent,
        resolve: {},
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.reporting.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'reporting/:orderId',
        component: ReportingOrderComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.reporting.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

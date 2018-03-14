import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { HighCommercialRiskComponent } from './high-commercial-risk.component';
import { HighCommercialRiskDetailComponent } from './high-commercial-risk-detail.component';
import { HighCommercialRiskPopupComponent } from './high-commercial-risk-dialog.component';
import { HighCommercialRiskDeletePopupComponent } from './high-commercial-risk-delete-dialog.component';

export const highCommercialRiskRoute: Routes = [
    {
        path: 'high-commercial-risk',
        component: HighCommercialRiskComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.highCommercialRisk.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'high-commercial-risk/:id',
        component: HighCommercialRiskDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.highCommercialRisk.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const highCommercialRiskPopupRoute: Routes = [
    {
        path: 'high-commercial-risk-new',
        component: HighCommercialRiskPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.highCommercialRisk.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'high-commercial-risk/:id/edit',
        component: HighCommercialRiskPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.highCommercialRisk.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'high-commercial-risk/:id/delete',
        component: HighCommercialRiskDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.highCommercialRisk.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

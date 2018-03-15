import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { IncidentComponent } from './incident.component';
import { IncidentDetailComponent } from './incident-detail.component';
import { IncidentPopupComponent } from './incident-dialog.component';
import { IncidentDeletePopupComponent } from './incident-delete-dialog.component';
import {IncidentUserOrderComponent} from "./incident-user/incident-user-order.component";
import {IncidentOrderComponent} from "./incident-order.component";
import {IncidentUserAllComponent} from "./incident-user/incident-user-all.component";

export const incidentRoute: Routes = [
    {
        path: 'incident',
        component: IncidentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.incident.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'incident/:orderId',
        component: IncidentOrderComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.incident.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const incidentPopupRoute: Routes = [
    {
        path: 'incident-new',
        component: IncidentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.incident.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'incident/:id/edit',
        component: IncidentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.incident.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'incident/:id/delete',
        component: IncidentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.incident.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

export const incidentUserRoute: Routes = [

    {
        path: 'incident-user/:orderId',
        component: IncidentUserOrderComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.incident.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'incident-user/:orderId/get-all',
        component: IncidentUserAllComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.incident.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {GlossaryManagementComponent} from "./glossary-management.component";
export const glossaryManagementRoute: Routes = [
    {
        path: 'glossary-management',
        component: GlossaryManagementComponent,
        resolve: {},
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.GlossaryManagement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

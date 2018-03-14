import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {JhiPaginationUtil} from 'ng-jhipster';

import {FilledCommercialRisksComponent} from './filled-commercial-risks.component';
import {FilledCommercialRisksDetailComponent} from './filled-commercial-risks-detail.component';
import {FilledCommercialRisksPopupComponent} from './filled-commercial-risks-dialog.component';
import {FilledCommercialRisksDeletePopupComponent} from './filled-commercial-risks-delete-dialog.component';

@Injectable()
export class FilledCommercialRisksResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const filledCommercialRisksRoute: Routes = [
    {
        path: 'filled-commercial-risks',
        component: FilledCommercialRisksComponent,
        resolve: {
            'pagingParams': FilledCommercialRisksResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.filledCommercialRisks.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'filled-commercial-risks/:id',
        component: FilledCommercialRisksDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.filledCommercialRisks.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const filledCommercialRisksPopupRoute: Routes = [
    {
        path: 'filled-commercial-risks-new',
        component: FilledCommercialRisksPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.filledCommercialRisks.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'filled-commercial-risks/:id/edit',
        component: FilledCommercialRisksPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.filledCommercialRisks.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'filled-commercial-risks/:id/delete',
        component: FilledCommercialRisksDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.filledCommercialRisks.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

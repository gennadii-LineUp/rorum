import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {JhiPaginationUtil} from 'ng-jhipster';

import {CommercialRisksPurposesComponent} from './commercial-risks-purposes.component';
import {CommercialRisksPurposesDetailComponent} from './commercial-risks-purposes-detail.component';
import {CommercialRisksPurposesPopupComponent} from './commercial-risks-purposes-dialog.component';
import {CommercialRisksPurposesDeletePopupComponent} from './commercial-risks-purposes-delete-dialog.component';

@Injectable()
export class CommercialRisksPurposesResolvePagingParams implements Resolve<any> {

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

export const commercialRisksPurposesRoute: Routes = [
    {
        path: 'commercial-risks-purposes',
        component: CommercialRisksPurposesComponent,
        resolve: {
            'pagingParams': CommercialRisksPurposesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.commercialRisksPurposes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'commercial-risks-purposes/:id',
        component: CommercialRisksPurposesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.commercialRisksPurposes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialRisksPurposesPopupRoute: Routes = [
    {
        path: 'commercial-risks-purposes-new',
        component: CommercialRisksPurposesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.commercialRisksPurposes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'commercial-risks-purposes/:id/edit',
        component: CommercialRisksPurposesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.commercialRisksPurposes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'commercial-risks-purposes/:id/delete',
        component: CommercialRisksPurposesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.commercialRisksPurposes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

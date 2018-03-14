import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {JhiPaginationUtil} from 'ng-jhipster';

import {RisksPurposesComponent} from './risks-purposes.component';
import {RisksPurposesDetailComponent} from './risks-purposes-detail.component';
import {RisksPurposesPopupComponent} from './risks-purposes-dialog.component';
import {RisksPurposesDeletePopupComponent} from './risks-purposes-delete-dialog.component';

@Injectable()
export class RisksPurposesResolvePagingParams implements Resolve<any> {

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

export const risksPurposesRoute: Routes = [
    {
        path: 'risks-purposes',
        component: RisksPurposesComponent,
        resolve: {
            'pagingParams': RisksPurposesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.risksPurposes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'risks-purposes/:id',
        component: RisksPurposesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.risksPurposes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const risksPurposesPopupRoute: Routes = [
    {
        path: 'risks-purposes-new',
        component: RisksPurposesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.risksPurposes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'risks-purposes/:id/edit',
        component: RisksPurposesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.risksPurposes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'risks-purposes/:id/delete',
        component: RisksPurposesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.risksPurposes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {JhiPaginationUtil} from 'ng-jhipster';

import {FilledRisksComponent} from './filled-risks.component';
import {FilledRisksDetailComponent} from './filled-risks-detail.component';
import {FilledRisksPopupComponent} from './filled-risks-dialog.component';
import {FilledRisksDeletePopupComponent} from './filled-risks-delete-dialog.component';

@Injectable()
export class FilledRisksResolvePagingParams implements Resolve<any> {

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

export const filledRisksRoute: Routes = [
    {
        path: 'filled-risks',
        component: FilledRisksComponent,
        resolve: {
            'pagingParams': FilledRisksResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.filledRisks.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'filled-risks/:id',
        component: FilledRisksDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.filledRisks.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const filledRisksPopupRoute: Routes = [
    {
        path: 'filled-risks-new',
        component: FilledRisksPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.filledRisks.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'filled-risks/:id/edit',
        component: FilledRisksPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.filledRisks.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'filled-risks/:id/delete',
        component: FilledRisksDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.filledRisks.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

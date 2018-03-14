import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {JhiPaginationUtil} from 'ng-jhipster';

import {SetOfSentPurposesComponent} from './set-of-sent-purposes.component';
import {SetOfSentPurposesDetailComponent} from './set-of-sent-purposes-detail.component';
import {SetOfSentPurposesPopupComponent} from './set-of-sent-purposes-dialog.component';
import {SetOfSentPurposesDeletePopupComponent} from './set-of-sent-purposes-delete-dialog.component';
import {SetOfSentPurposesCheckComponent} from './set-of-sent-purposes-check.component';

@Injectable()
export class SetOfSentPurposesResolvePagingParams implements Resolve<any> {

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

export const setOfSentPurposesRoute: Routes = [
    {// :parent_type
        path: 'orders-admin/:id_order/set-of-sent-purposes/:parent_type',
        component: SetOfSentPurposesComponent,
        resolve: {
            'pagingParams': SetOfSentPurposesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'rorumApp.setOfSentPurposes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'orders-admin/:id_order/set-of-sent-purposes/:parent_type',
        component: SetOfSentPurposesComponent,
        resolve: {
            'pagingParams': SetOfSentPurposesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'rorumApp.setOfSentPurposes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'orders-admin/:id_order/set-of-sent-purposes/:parent_type/:id_set_purpose',
        component: SetOfSentPurposesDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'rorumApp.setOfSentPurposes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'orders-admin/:id_order/set-of-sent-purposes/:parent_type/:id_set_purpose/check',
        component: SetOfSentPurposesCheckComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'rorumApp.setOfSentPurposes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const setOfSentPurposesPopupRoute: Routes = [
    {
        path: 'reject',
        // path: ':parent_type/:id_set_purpose/reject',
        component: SetOfSentPurposesPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'rorumApp.setOfSentPurposes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'set-of-sent-purposes/:parent_type/:id_set_purpose/edit',
        component: SetOfSentPurposesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.setOfSentPurposes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: ':id_set_purpose/delete',
        component: SetOfSentPurposesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.setOfSentPurposes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {JhiPaginationUtil} from 'ng-jhipster';

import {FilledMeasureUnitsComponent} from './filled-measure-units.component';
import {FilledMeasureUnitsDetailComponent} from './filled-measure-units-detail.component';
import {FilledMeasureUnitsPopupComponent} from './filled-measure-units-dialog.component';
import {FilledMeasureUnitsDeletePopupComponent} from './filled-measure-units-delete-dialog.component';

@Injectable()
export class FilledMeasureUnitsResolvePagingParams implements Resolve<any> {

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

export const filledMeasureUnitsRoute: Routes = [
    {
        path: 'filled-measure-units',
        component: FilledMeasureUnitsComponent,
        resolve: {
            'pagingParams': FilledMeasureUnitsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.filledMeasureUnits.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'filled-measure-units/:id',
        component: FilledMeasureUnitsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.filledMeasureUnits.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const filledMeasureUnitsPopupRoute: Routes = [
    {
        path: 'filled-measure-units-new',
        component: FilledMeasureUnitsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.filledMeasureUnits.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'filled-measure-units/:id/edit',
        component: FilledMeasureUnitsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.filledMeasureUnits.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'filled-measure-units/:id/delete',
        component: FilledMeasureUnitsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.filledMeasureUnits.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

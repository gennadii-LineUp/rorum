import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {JhiPaginationUtil} from 'ng-jhipster';

import {MeasureUnitsPurposesComponent} from './measure-units-purposes.component';
import {MeasureUnitsPurposesDetailComponent} from './measure-units-purposes-detail.component';
import {MeasureUnitsPurposesPopupComponent} from './measure-units-purposes-dialog.component';
import {MeasureUnitsPurposesDeletePopupComponent} from './measure-units-purposes-delete-dialog.component';

@Injectable()
export class MeasureUnitsPurposesResolvePagingParams implements Resolve<any> {

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

export const measureUnitsPurposesRoute: Routes = [
    {
        path: 'measure-units-purposes',
        component: MeasureUnitsPurposesComponent,
        resolve: {
            'pagingParams': MeasureUnitsPurposesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.measureUnitsPurposes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'measure-units-purposes/:id',
        component: MeasureUnitsPurposesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.measureUnitsPurposes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const measureUnitsPurposesPopupRoute: Routes = [
    {
        path: 'measure-units-purposes-new',
        component: MeasureUnitsPurposesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.measureUnitsPurposes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'measure-units-purposes/:id/edit',
        component: MeasureUnitsPurposesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.measureUnitsPurposes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'measure-units-purposes/:id/delete',
        component: MeasureUnitsPurposesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.measureUnitsPurposes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

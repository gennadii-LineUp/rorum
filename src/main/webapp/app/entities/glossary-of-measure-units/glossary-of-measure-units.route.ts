import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {JhiPaginationUtil} from 'ng-jhipster';

import {GlossaryOfMeasureUnitsComponent} from './glossary-of-measure-units.component';
import {GlossaryOfMeasureUnitsDetailComponent} from './glossary-of-measure-units-detail.component';
import {GlossaryOfMeasureUnitsPopupComponent} from './glossary-of-measure-units-dialog.component';
import {GlossaryOfMeasureUnitsDeletePopupComponent} from './glossary-of-measure-units-delete-dialog.component';

@Injectable()
export class GlossaryOfMeasureUnitsResolvePagingParams implements Resolve<any> {

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

export const glossaryOfMeasureUnitsRoute: Routes = [
    {
        path: 'glossary-of-measure-units',
        component: GlossaryOfMeasureUnitsComponent,
        resolve: {
            'pagingParams': GlossaryOfMeasureUnitsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfMeasureUnits.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'glossary-of-measure-units/:id',
        component: GlossaryOfMeasureUnitsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfMeasureUnits.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const glossaryOfMeasureUnitsPopupRoute: Routes = [
    {
        path: 'glossary-of-measure-units-new',
        component: GlossaryOfMeasureUnitsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfMeasureUnitsDTO.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'glossary-of-measure-units/:id/edit',
        component: GlossaryOfMeasureUnitsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfMeasureUnitsDTO.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'glossary-of-measure-units/:id/delete',
        component: GlossaryOfMeasureUnitsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfMeasureUnitsDTO.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

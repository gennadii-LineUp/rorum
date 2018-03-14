import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {JhiPaginationUtil} from 'ng-jhipster';

import {GlossaryOfRisksComponent} from './glossary-of-risks.component';
import {GlossaryOfRisksDetailComponent} from './glossary-of-risks-detail.component';
import {GlossaryOfRisksPopupComponent} from './glossary-of-risks-dialog.component';
import {GlossaryOfRisksDeletePopupComponent} from './glossary-of-risks-delete-dialog.component';

@Injectable()
export class GlossaryOfRisksResolvePagingParams implements Resolve<any> {

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

export const glossaryOfRisksRoute: Routes = [
    {
        path: 'glossary-of-risks',
        component: GlossaryOfRisksComponent,
        resolve: {
            'pagingParams': GlossaryOfRisksResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfRisks.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'glossary-of-risks/:id',
        component: GlossaryOfRisksDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfRisks.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const glossaryOfRisksPopupRoute: Routes = [
    {
        path: 'glossary-of-risks-new',
        component: GlossaryOfRisksPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfRisksDTO.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'glossary-of-risks/:id/edit',
        component: GlossaryOfRisksPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfRisksDTO.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'glossary-of-risks/:id/delete',
        component: GlossaryOfRisksDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfRisksDTO.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

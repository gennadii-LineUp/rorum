import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {JhiPaginationUtil} from 'ng-jhipster';

import {GlossaryOfCommercialRisksComponent} from './glossary-of-commercial-risks.component';
import {GlossaryOfCommercialRisksDetailComponent} from './glossary-of-commercial-risks-detail.component';
import {GlossaryOfCommercialRisksPopupComponent} from './glossary-of-commercial-risks-dialog.component';
import {GlossaryOfCommercialRisksDeletePopupComponent} from './glossary-of-commercial-risks-delete-dialog.component';

@Injectable()
export class GlossaryOfCommercialRisksResolvePagingParams implements Resolve<any> {

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

export const glossaryOfCommercialRisksRoute: Routes = [
    {
        path: 'glossary-of-commercial-risks',
        component: GlossaryOfCommercialRisksComponent,
        resolve: {
            'pagingParams': GlossaryOfCommercialRisksResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfCommercialRisks.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'glossary-of-commercial-risks/:id',
        component: GlossaryOfCommercialRisksDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfCommercialRisks.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const glossaryOfCommercialRisksPopupRoute: Routes = [
    {
        path: 'glossary-of-commercial-risks-new',
        component: GlossaryOfCommercialRisksPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfCommercialRisksDTO.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'glossary-of-commercial-risks/:id/edit',
        component: GlossaryOfCommercialRisksPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfCommercialRisksDTO.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'glossary-of-commercial-risks/:id/delete',
        component: GlossaryOfCommercialRisksDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfCommercialRisksDTO.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

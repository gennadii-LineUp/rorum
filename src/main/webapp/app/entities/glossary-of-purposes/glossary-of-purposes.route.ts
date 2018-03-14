import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {JhiPaginationUtil} from 'ng-jhipster';

import {GlossaryOfPurposesComponent} from './glossary-of-purposes.component';
import {GlossaryOfPurposesDetailComponent} from './glossary-of-purposes-detail.component';
import {GlossaryOfPurposesPopupComponent} from './glossary-of-purposes-dialog.component';
import {GlossaryOfPurposesDeletePopupComponent} from './glossary-of-purposes-delete-dialog.component';

@Injectable()
export class GlossaryOfPurposesResolvePagingParams implements Resolve<any> {

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

export const glossaryOfPurposesRoute: Routes = [
    {
        path: 'glossary-of-purposes',
        component: GlossaryOfPurposesComponent,
        resolve: {
            'pagingParams': GlossaryOfPurposesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfPurposes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'glossary-of-purposes/:id',
        component: GlossaryOfPurposesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfPurposes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const glossaryOfPurposesPopupRoute: Routes = [
    {
        path: 'glossary-of-purposes-new',
        component: GlossaryOfPurposesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfRisksService.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'glossary-of-purposes/:id/edit',
        component: GlossaryOfPurposesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfRisksService.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'glossary-of-purposes/:id/delete',
        component: GlossaryOfPurposesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfRisksService.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

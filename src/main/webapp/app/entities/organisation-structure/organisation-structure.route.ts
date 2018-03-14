import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {JhiPaginationUtil} from 'ng-jhipster';

import {OrganisationStructureComponent} from './organisation-structure.component';
import {OrganisationStructureDetailComponent} from './organisation-structure-detail.component';
import {OrganisationStructurePopupComponent} from './organisation-structure-dialog.component';
import {OrganisationStructureDeletePopupComponent} from './organisation-structure-delete-dialog.component';

@Injectable()
export class OrganisationStructureResolvePagingParams implements Resolve<any> {

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

export const organisationStructureRoute: Routes = [
    {
        path: 'organisation-structure',
        component: OrganisationStructureComponent,
        resolve: {
            'pagingParams': OrganisationStructureResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.organisationStructure.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'organisation-structure/:id',
        component: OrganisationStructureDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.organisationStructure.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const organisationStructurePopupRoute: Routes = [
    {
        path: 'organisation-structure-new',
        component: OrganisationStructurePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.organisationStructure.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'organisation-structure/:id/edit',
        component: OrganisationStructurePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.organisationStructure.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'organisation-structure/:id/delete',
        component: OrganisationStructureDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.organisationStructure.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

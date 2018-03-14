import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {JhiPaginationUtil} from 'ng-jhipster';

import {GlossaryOfProcessesComponent} from './glossary-of-processes.component';
import {GlossaryOfProcessesDetailComponent} from './glossary-of-processes-detail.component';
import {GlossaryOfProcessesPopupComponent} from './glossary-of-processes-dialog.component';
import {GlossaryOfProcessesDeletePopupComponent} from './glossary-of-processes-delete-dialog.component';

@Injectable()
export class GlossaryOfProcessesResolvePagingParams implements Resolve<any> {

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

export const glossaryOfProcessesRoute: Routes = [
    {
        path: 'glossary-of-processes',
        component: GlossaryOfProcessesComponent,
        resolve: {
            'pagingParams': GlossaryOfProcessesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfProcesses.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'glossary-of-processes/:id',
        component: GlossaryOfProcessesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfProcesses.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const glossaryOfProcessesPopupRoute: Routes = [
    {
        path: 'glossary-of-processes-new',
        component: GlossaryOfProcessesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfProcesses.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'glossary-of-processes/:id/edit',
        component: GlossaryOfProcessesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfProcesses.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'glossary-of-processes/:id/delete',
        component: GlossaryOfProcessesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.glossaryOfProcesses.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

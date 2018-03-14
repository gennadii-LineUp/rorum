import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {JhiPaginationUtil} from 'ng-jhipster';

import {OrdersComponent} from './orders.component';
import {OrdersDetailComponent} from './orders-detail.component';
import {OrdersPopupComponent} from './orders-dialog.component';
import {OrdersDeletePopupComponent} from './orders-delete-dialog.component';
import {OrderCeleComponent} from './order-cele.component';
import {OrdersUserComponent} from './orders-user.component';

@Injectable()
export class OrdersResolvePagingParams implements Resolve<any> {

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

export const ordersRoute: Routes = [
    {
        path: 'orders-user',
        component: OrdersUserComponent,
        resolve: {
            'pagingParams': OrdersResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.orders.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'orders-user/:id',
        component: OrdersDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.orders.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'orders-user/:id/purposes',
        component: OrderCeleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.order.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'orders-admin',
        component: OrdersComponent,
        resolve: {
            'pagingParams': OrdersResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'rorumApp.orders.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'orders-admin/:id',
        component: OrdersDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'rorumApp.orders.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'orders-admin/:id/purposes',
        component: OrderCeleComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'rorumApp.order.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ordersPopupRoute: Routes = [
    {
        path: 'orders-user-new',
        component: OrdersPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.orders.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'orders-user/:id/edit',
        component: OrdersPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.orders.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'orders-user/:id/delete',
        component: OrdersDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rorumApp.orders.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
    path: 'orders-admin-new',
    component: OrdersPopupComponent,
    data: {
    authorities: ['ROLE_ADMIN'],
        pageTitle: 'rorumApp.orders.home.title'
    },
    canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'orders-admin/:id/edit',
            component: OrdersPopupComponent,
        data: {
        authorities: ['ROLE_ADMIN'],
            pageTitle: 'rorumApp.orders.home.title'
    },
        canActivate: [UserRouteAccessService],
            outlet: 'popup'
    },
    {
        path: 'orders-admin/:id/delete',
            component: OrdersDeletePopupComponent,
        data: {
        authorities: ['ROLE_ADMIN'],
            pageTitle: 'rorumApp.orders.home.title'
    },
        canActivate: [UserRouteAccessService],
            outlet: 'popup'
    }

];

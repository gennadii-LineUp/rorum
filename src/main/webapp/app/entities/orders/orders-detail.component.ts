import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';
import 'rxjs/add/operator/takeWhile';

import {Orders} from './models/orders.model';
import {OrdersService} from './orders.service';

@Component({
    selector: 'jhi-orders-detail',
    templateUrl: './orders-detail.component.html'
})
export class OrdersDetailComponent implements OnInit, OnDestroy {
    orders: Orders;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    alive = true;

    constructor(
        private eventManager: JhiEventManager,
        private ordersService: OrdersService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrders();
    }

    load(id) {
        this.ordersService.find(id)
            .takeWhile(() => this.alive)
            .subscribe((orders) => {
            this.orders = orders;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
        this.alive = false;
    }

    registerChangeInOrders() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ordersListModification',
            (response) => this.load(this.orders.id)
        );
    }
}

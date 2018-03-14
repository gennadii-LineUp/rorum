import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';

import {SetOfSentPurposes} from './set-of-sent-purposes.model';
import {SetOfSentPurposesService} from './set-of-sent-purposes.service';
import {ITEMS_PER_PAGE, Principal, ResponseWrapper} from '../../shared';
import {Orders} from '../orders/models/orders.model';
import {OrdersService} from '../orders';
import 'rxjs/add/operator/takeWhile';

@Component({
    selector: 'jhi-set-of-sent-purposes',
    templateUrl: './set-of-sent-purposes.component.html',
    providers: [OrdersService]
})
export class SetOfSentPurposesComponent implements OnInit, OnDestroy {

currentAccount: any;
    setOfSentPurposes: Array<SetOfSentPurposes>;
    order_active: number;
    order: Orders;
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    private subscription: Subscription;
    alive = true;
    parent_type: string;

    constructor(
        private setOfSentPurposesService: SetOfSentPurposesService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private ordersService: OrdersService,
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.parent_type = params['parent_type'];
            this.loadCurrentOrder(+params['id_order']);
            this.order_active = +params['id_order'];
            this.loadAll(this.order_active, this.parent_type);

            // this.setOfSentPurposesService.setSetPurposesId(this.set_purposes_id);
            this.setOfSentPurposesService.setOrderId(this.order_active);
            this.setOfSentPurposesService.setParentType(this.parent_type);
            // this.ordersService.getListOfSentPurposes(+params['id_order'])
            //     .takeWhile(() => this.alive)
            //     .subscribe((res: any) => {
            //         console.log(res);
            //     },
            //     (res: ResponseWrapper) => this.onError(res.json)
            // );
        });

        this.principal.identity().then((account) => {
            this.currentAccount = account;
            console.log(this.currentAccount);
        });
        this.registerChangeInSetOfSentPurposes();

    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
        this.alive = false;
    }

    loadAll(id_order: number, parent_type: string) {
        this.setOfSentPurposesService.query_new(id_order, parent_type, {
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()})
            .takeWhile(() => this.alive)
            .subscribe(
            (res: ResponseWrapper) => {
                // console.log(res.json);
                this.onSuccess(res.json, res.headers);
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    private onSuccess(data, headers) {
        // this.setOfSentPurposesService.query({
        //             page: this.page - 1,
        //             size: this.itemsPerPage,
        //             sort: this.sort()})
        //     .takeWhile(() => this.alive)
        //     .subscribe((res: ResponseWrapper) => {
        //             console.log(res.json);
        //             console.log(res.headers);
        //             const _data = res.json;
        //
        //             this.totalItems = _data.length;
        //             this.queryCount = _data.length;
        //             this.links = this.parseLinks.parse(res.headers.get('link'));
        //             // this.page = pagingParams.page;
        //         },
        //         (res: ResponseWrapper) => this.onError(res.json));

        this.setOfSentPurposes = data;
        console.log(this.setOfSentPurposes);
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['/orders/' + this.order_active + '/set-of-sent-purposes'], {queryParams:
            {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll(this.order_active, this.parent_type);
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/set-of-sent-purposes', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll(this.order_active, this.parent_type);
    }

    trackId(index: number, item: SetOfSentPurposes) {
        return item.id;
    }
    registerChangeInSetOfSentPurposes() {
        this.eventSubscriber = this.eventManager.subscribe('setOfSentPurposesListModification', (response) => this.loadAll(this.order_active, this.parent_type));
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    loadCurrentOrder(id) {
        this.ordersService.find(id)
            .takeWhile(() => this.alive)
            .subscribe((order: Orders) => {
            this.order = order;
            console.log(this.order.name);
        });
    }
    // generateRiskRegisterReport(orderId: number, organisationStructureId: number, filename: String) {
    //     console.log(filename);
    //     // const filename: String = "123" + Date.now().toLocaleString;
    //     this.setOfSentPurposesService.generateRiskRegisterReport(orderId, organisationStructureId)
    //         .then(((file) => {
    //             const fileURL = URL.createObjectURL(file);
    //             const a = document.createElement('a');
    //             document.body.appendChild(a);
    //             a.href = fileURL;
    //             a.download = filename + ".xlsx";
    //             a.click();
    //             // this.isPrinting[i] = false;
    //             // this.isUpdating = false;
    //         }))
    //         .catch((error) => {
    //             // this.isPrinting[i] = false;
    //             // this.isUpdating = false;
    //             console.log('consumer error ' + error)
    //         });
    // }

    //    generatePurposeAccomplishmentRaport(orderId: number, userId: number, filename: String) {
    //     console.log(filename);
    //     // const filename: String = "123" + Date.now().toLocaleString;
    //     this.setOfSentPurposesService.generatePurposeAccomplishmentRaport(orderId, userId)
    //         .then(((file) => {
    //             const fileURL = URL.createObjectURL(file);
    //             const a = document.createElement('a');
    //             document.body.appendChild(a);
    //             a.href = fileURL;
    //             a.download = filename + ".xlsx";
    //             a.click();
    //         }))
    //         .catch((error) => {
    //             console.log('consumer error ' + error)
    //         });
    // }

}

import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import { ReportingService } from '.';
import { Http } from '@angular/http';
import { SERVER_API_URL } from '../../app.constants';
import { ResponseWrapper, ITEMS_PER_PAGE } from '../../shared';
import { Orders } from '../orders';

@Component({
    selector: 'reporting',
    templateUrl: './reporting.component.html'
})
export class ReportingComponent implements OnInit, OnDestroy {
    queryCount: any;
    links: any;
    totalItems: any;
    reverse: any;
    predicate: string;
    isActive: boolean;
    orders: Orders[];
    test: any;

    constructor(
        private activatedRoute: ActivatedRoute,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private reportingService: ReportingService
    ) { }

    ngOnInit(): void {
        this.isActive = false;
        this.loadAll();
    }

    ngOnDestroy(): void {
        // console.log("ngOnDestroy");
    }

    loadAll() {
        this.reportingService.loadAll().subscribe(
            (res) => {
                // console.log(res);
                // this.test = res
                this.orders = res.json},
            (error) => {console.error(error)}
        );
        // console.log(this.test);
    }
    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }
    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.orders = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    private loadOrders(res: ResponseWrapper) {
        this.orders = res.json;
    }
    trackId(index: number, item: Orders) {
        return item.id;
    }
}

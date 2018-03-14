import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {HighRisk} from './high-risk.model';
import {HighRiskService} from './high-risk.service';
import {Principal, ResponseWrapper} from '../../shared';

@Component({
    selector: 'jhi-high-risk',
    templateUrl: './high-risk.component.html'
})
export class HighRiskComponent implements OnInit, OnDestroy {
highRisks: HighRisk[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private highRiskService: HighRiskService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.highRiskService.query().subscribe(
            (res: ResponseWrapper) => {
                this.highRisks = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInHighRisks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: HighRisk) {
        return item.id;
    }
    registerChangeInHighRisks() {
        this.eventSubscriber = this.eventManager.subscribe('highRiskListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

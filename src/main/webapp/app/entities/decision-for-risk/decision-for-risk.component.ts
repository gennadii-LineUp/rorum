import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {DecisionForRisk} from './decision-for-risk.model';
import {DecisionForRiskService} from './decision-for-risk.service';
import {Principal, ResponseWrapper} from '../../shared';

@Component({
    selector: 'jhi-decision-for-risk',
    templateUrl: './decision-for-risk.component.html'
})
export class DecisionForRiskComponent implements OnInit, OnDestroy {
decisionForRisks: DecisionForRisk[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private decisionForRiskService: DecisionForRiskService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.decisionForRiskService.query().subscribe(
            (res: ResponseWrapper) => {
                this.decisionForRisks = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDecisionForRisks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DecisionForRisk) {
        return item.id;
    }
    registerChangeInDecisionForRisks() {
        this.eventSubscriber = this.eventManager.subscribe('decisionForRiskListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

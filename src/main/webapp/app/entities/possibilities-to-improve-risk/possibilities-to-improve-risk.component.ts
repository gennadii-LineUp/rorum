import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {PossibilitiesToImproveRisk} from './possibilities-to-improve-risk.model';
import {PossibilitiesToImproveRiskService} from './possibilities-to-improve-risk.service';
import {Principal, ResponseWrapper} from '../../shared';

@Component({
    selector: 'jhi-possibilities-to-improve-risk',
    templateUrl: './possibilities-to-improve-risk.component.html'
})
export class PossibilitiesToImproveRiskComponent implements OnInit, OnDestroy {
possibilitiesToImproveRisks: PossibilitiesToImproveRisk[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private possibilitiesToImproveRiskService: PossibilitiesToImproveRiskService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.possibilitiesToImproveRiskService.query().subscribe(
            (res: ResponseWrapper) => {
                this.possibilitiesToImproveRisks = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPossibilitiesToImproveRisks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PossibilitiesToImproveRisk) {
        return item.id;
    }
    registerChangeInPossibilitiesToImproveRisks() {
        this.eventSubscriber = this.eventManager.subscribe('possibilitiesToImproveRiskListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

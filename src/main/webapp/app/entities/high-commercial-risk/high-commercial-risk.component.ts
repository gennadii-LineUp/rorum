import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { HighCommercialRisk } from './high-commercial-risk.model';
import { HighCommercialRiskService } from './high-commercial-risk.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-high-commercial-risk',
    templateUrl: './high-commercial-risk.component.html'
})
export class HighCommercialRiskComponent implements OnInit, OnDestroy {
highCommercialRisks: HighCommercialRisk[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private highCommercialRiskService: HighCommercialRiskService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.highCommercialRiskService.query().subscribe(
            (res: ResponseWrapper) => {
                this.highCommercialRisks = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInHighCommercialRisks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: HighCommercialRisk) {
        return item.id;
    }
    registerChangeInHighCommercialRisks() {
        this.eventSubscriber = this.eventManager.subscribe('highCommercialRiskListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

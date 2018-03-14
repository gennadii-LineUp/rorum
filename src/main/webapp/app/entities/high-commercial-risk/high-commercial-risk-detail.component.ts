import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { HighCommercialRisk } from './high-commercial-risk.model';
import { HighCommercialRiskService } from './high-commercial-risk.service';

@Component({
    selector: 'jhi-high-commercial-risk-detail',
    templateUrl: './high-commercial-risk-detail.component.html'
})
export class HighCommercialRiskDetailComponent implements OnInit, OnDestroy {

    highCommercialRisk: HighCommercialRisk;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private highCommercialRiskService: HighCommercialRiskService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHighCommercialRisks();
    }

    load(id) {
        this.highCommercialRiskService.find(id).subscribe((highCommercialRisk) => {
            this.highCommercialRisk = highCommercialRisk;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHighCommercialRisks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'highCommercialRiskListModification',
            (response) => this.load(this.highCommercialRisk.id)
        );
    }
}

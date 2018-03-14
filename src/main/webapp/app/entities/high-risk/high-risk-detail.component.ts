import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';

import {HighRisk} from './high-risk.model';
import {HighRiskService} from './high-risk.service';

@Component({
    selector: 'jhi-high-risk-detail',
    templateUrl: './high-risk-detail.component.html'
})
export class HighRiskDetailComponent implements OnInit, OnDestroy {

    highRisk: HighRisk;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private highRiskService: HighRiskService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHighRisks();
    }

    load(id) {
        this.highRiskService.find(id).subscribe((highRisk) => {
            this.highRisk = highRisk;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHighRisks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'highRiskListModification',
            (response) => this.load(this.highRisk.id)
        );
    }
}

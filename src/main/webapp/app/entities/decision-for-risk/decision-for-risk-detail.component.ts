import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';

import {DecisionForRisk} from './decision-for-risk.model';
import {DecisionForRiskService} from './decision-for-risk.service';

@Component({
    selector: 'jhi-decision-for-risk-detail',
    templateUrl: './decision-for-risk-detail.component.html'
})
export class DecisionForRiskDetailComponent implements OnInit, OnDestroy {

    decisionForRisk: DecisionForRisk;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private decisionForRiskService: DecisionForRiskService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDecisionForRisks();
    }

    load(id) {
        this.decisionForRiskService.find(id).subscribe((decisionForRisk) => {
            this.decisionForRisk = decisionForRisk;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDecisionForRisks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'decisionForRiskListModification',
            (response) => this.load(this.decisionForRisk.id)
        );
    }
}

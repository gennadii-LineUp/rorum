import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';

import {PossibilitiesToImproveRisk} from './possibilities-to-improve-risk.model';
import {PossibilitiesToImproveRiskService} from './possibilities-to-improve-risk.service';

@Component({
    selector: 'jhi-possibilities-to-improve-risk-detail',
    templateUrl: './possibilities-to-improve-risk-detail.component.html'
})
export class PossibilitiesToImproveRiskDetailComponent implements OnInit, OnDestroy {

    possibilitiesToImproveRisk: PossibilitiesToImproveRisk;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private possibilitiesToImproveRiskService: PossibilitiesToImproveRiskService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPossibilitiesToImproveRisks();
    }

    load(id) {
        this.possibilitiesToImproveRiskService.find(id).subscribe((possibilitiesToImproveRisk) => {
            this.possibilitiesToImproveRisk = possibilitiesToImproveRisk;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPossibilitiesToImproveRisks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'possibilitiesToImproveRiskListModification',
            (response) => this.load(this.possibilitiesToImproveRisk.id)
        );
    }
}

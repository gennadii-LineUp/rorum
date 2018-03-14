import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';

import {RisksPurposes} from './risks-purposes.model';
import {RisksPurposesService} from './risks-purposes.service';

@Component({
    selector: 'jhi-risks-purposes-detail',
    templateUrl: './risks-purposes-detail.component.html'
})
export class RisksPurposesDetailComponent implements OnInit, OnDestroy {

    risksPurposes: RisksPurposes;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private risksPurposesService: RisksPurposesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRisksPurposes();
    }

    load(id) {
        this.risksPurposesService.find(id).subscribe((risksPurposes) => {
            this.risksPurposes = risksPurposes;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRisksPurposes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'risksPurposesListModification',
            (response) => this.load(this.risksPurposes.id)
        );
    }
}

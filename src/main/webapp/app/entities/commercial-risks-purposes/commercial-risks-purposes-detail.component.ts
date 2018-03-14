import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';

import {CommercialRisksPurposes} from './commercial-risks-purposes.model';
import {CommercialRisksPurposesService} from './commercial-risks-purposes.service';

@Component({
    selector: 'jhi-commercial-risks-purposes-detail',
    templateUrl: './commercial-risks-purposes-detail.component.html'
})
export class CommercialRisksPurposesDetailComponent implements OnInit, OnDestroy {

    commercialRisksPurposes: CommercialRisksPurposes;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private commercialRisksPurposesService: CommercialRisksPurposesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCommercialRisksPurposes();
    }

    load(id) {
        this.commercialRisksPurposesService.find(id).subscribe((commercialRisksPurposes) => {
            this.commercialRisksPurposes = commercialRisksPurposes;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCommercialRisksPurposes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'commercialRisksPurposesListModification',
            (response) => this.load(this.commercialRisksPurposes.id)
        );
    }
}

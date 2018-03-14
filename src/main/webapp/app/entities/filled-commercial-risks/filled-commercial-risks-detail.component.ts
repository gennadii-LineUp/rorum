import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';

import {FilledCommercialRisks} from './filled-commercial-risks.model';
import {FilledCommercialRisksService} from './filled-commercial-risks.service';

@Component({
    selector: 'jhi-filled-commercial-risks-detail',
    templateUrl: './filled-commercial-risks-detail.component.html'
})
export class FilledCommercialRisksDetailComponent implements OnInit, OnDestroy {

    filledCommercialRisks: FilledCommercialRisks;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private filledCommercialRisksService: FilledCommercialRisksService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFilledCommercialRisks();
    }

    load(id) {
        this.filledCommercialRisksService.find(id).subscribe((filledCommercialRisks) => {
            this.filledCommercialRisks = filledCommercialRisks;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFilledCommercialRisks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'filledCommercialRisksListModification',
            (response) => this.load(this.filledCommercialRisks.id)
        );
    }
}

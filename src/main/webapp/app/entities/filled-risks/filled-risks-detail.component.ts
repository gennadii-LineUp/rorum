import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';

import {FilledRisks} from './filled-risks.model';
import {FilledRisksService} from './filled-risks.service';

@Component({
    selector: 'jhi-filled-risks-detail',
    templateUrl: './filled-risks-detail.component.html'
})
export class FilledRisksDetailComponent implements OnInit, OnDestroy {

    filledRisks: FilledRisks;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private filledRisksService: FilledRisksService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFilledRisks();
    }

    load(id) {
        this.filledRisksService.find(id).subscribe((filledRisks) => {
            this.filledRisks = filledRisks;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFilledRisks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'filledRisksListModification',
            (response) => this.load(this.filledRisks.id)
        );
    }
}

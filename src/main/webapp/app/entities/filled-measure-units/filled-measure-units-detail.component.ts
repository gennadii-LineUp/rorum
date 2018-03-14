import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';

import {FilledMeasureUnits} from './filled-measure-units.model';
import {FilledMeasureUnitsService} from './filled-measure-units.service';

@Component({
    selector: 'jhi-filled-measure-units-detail',
    templateUrl: './filled-measure-units-detail.component.html'
})
export class FilledMeasureUnitsDetailComponent implements OnInit, OnDestroy {

    filledMeasureUnits: FilledMeasureUnits;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private filledMeasureUnitsService: FilledMeasureUnitsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFilledMeasureUnits();
    }

    load(id) {
        this.filledMeasureUnitsService.find(id).subscribe((filledMeasureUnits) => {
            this.filledMeasureUnits = filledMeasureUnits;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFilledMeasureUnits() {
        this.eventSubscriber = this.eventManager.subscribe(
            'filledMeasureUnitsListModification',
            (response) => this.load(this.filledMeasureUnits.id)
        );
    }
}

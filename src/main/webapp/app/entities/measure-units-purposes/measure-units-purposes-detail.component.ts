import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';

import {MeasureUnitsPurposes} from './measure-units-purposes.model';
import {MeasureUnitsPurposesService} from './measure-units-purposes.service';

@Component({
    selector: 'jhi-measure-units-purposes-detail',
    templateUrl: './measure-units-purposes-detail.component.html'
})
export class MeasureUnitsPurposesDetailComponent implements OnInit, OnDestroy {

    measureUnitsPurposes: MeasureUnitsPurposes;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private measureUnitsPurposesService: MeasureUnitsPurposesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMeasureUnitsPurposes();
    }

    load(id) {
        this.measureUnitsPurposesService.find(id).subscribe((measureUnitsPurposes) => {
            this.measureUnitsPurposes = measureUnitsPurposes;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMeasureUnitsPurposes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'measureUnitsPurposesListModification',
            (response) => this.load(this.measureUnitsPurposes.id)
        );
    }
}

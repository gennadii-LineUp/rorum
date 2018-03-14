import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';

import {GlossaryOfMeasureUnits} from './glossary-of-measure-units.model';
import {GlossaryOfMeasureUnitsService} from './glossary-of-measure-units.service';

@Component({
    selector: 'jhi-glossary-of-measure-units-detail',
    templateUrl: './glossary-of-measure-units-detail.component.html'
})
export class GlossaryOfMeasureUnitsDetailComponent implements OnInit, OnDestroy {

    glossaryOfMeasureUnitsDTO: GlossaryOfMeasureUnits;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private glossaryOfMeasureUnitsService: GlossaryOfMeasureUnitsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGlossaryOfMeasureUnits();
    }

    load(id) {
        this.glossaryOfMeasureUnitsService.find(id).subscribe((glossaryOfMeasureUnitsDTO) => {
            this.glossaryOfMeasureUnitsDTO = glossaryOfMeasureUnitsDTO;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGlossaryOfMeasureUnits() {
        this.eventSubscriber = this.eventManager.subscribe(
            'glossaryOfMeasureUnitsListModification',
            (response) => this.load(this.glossaryOfMeasureUnitsDTO.id)
        );
    }
}

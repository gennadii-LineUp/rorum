import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';

import {GlossaryOfCommercialRisks} from './glossary-of-commercial-risks.model';
import {GlossaryOfCommercialRisksService} from './glossary-of-commercial-risks.service';

@Component({
    selector: 'jhi-glossary-of-commercial-risks-detail',
    templateUrl: './glossary-of-commercial-risks-detail.component.html'
})
export class GlossaryOfCommercialRisksDetailComponent implements OnInit, OnDestroy {

    glossaryOfCommercialRisksDTO: GlossaryOfCommercialRisks;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private glossaryOfCommercialRisksService: GlossaryOfCommercialRisksService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGlossaryOfCommercialRisks();
    }

    load(id) {
        this.glossaryOfCommercialRisksService.find(id).subscribe((glossaryOfCommercialRisksDTO) => {
            this.glossaryOfCommercialRisksDTO = glossaryOfCommercialRisksDTO;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGlossaryOfCommercialRisks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'glossaryOfCommercialRisksListModification',
            (response) => this.load(this.glossaryOfCommercialRisksDTO.id)
        );
    }
}

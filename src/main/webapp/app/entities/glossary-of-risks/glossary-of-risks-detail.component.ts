import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';

import {GlossaryOfRisks} from './glossary-of-risks.model';
import {GlossaryOfRisksService} from './glossary-of-risks.service';

@Component({
    selector: 'jhi-glossary-of-risks-detail',
    templateUrl: './glossary-of-risks-detail.component.html'
})
export class GlossaryOfRisksDetailComponent implements OnInit, OnDestroy {

    glossaryOfRisksDTO: GlossaryOfRisks;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private glossaryOfRisksService: GlossaryOfRisksService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGlossaryOfRisks();
    }

    load(id) {
        this.glossaryOfRisksService.find(id).subscribe((glossaryOfRisksDTO) => {
            this.glossaryOfRisksDTO = glossaryOfRisksDTO;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGlossaryOfRisks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'glossaryOfRisksListModification',
            (response) => this.load(this.glossaryOfRisksDTO.id)
        );
    }
}

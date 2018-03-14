import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';

import {GlossaryOfPurposes} from './glossary-of-purposes.model';
import {GlossaryOfPurposesService} from './glossary-of-purposes.service';

@Component({
    selector: 'jhi-glossary-of-purposes-detail',
    templateUrl: './glossary-of-purposes-detail.component.html'
})
export class GlossaryOfPurposesDetailComponent implements OnInit, OnDestroy {

    glossaryOfRisksService: GlossaryOfPurposes;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private glossaryOfPurposesService: GlossaryOfPurposesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGlossaryOfPurposes();
    }

    load(id) {
        this.glossaryOfPurposesService.find(id).subscribe((glossaryOfRisksService) => {
            this.glossaryOfRisksService = glossaryOfRisksService;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGlossaryOfPurposes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'glossaryOfPurposesListModification',
            (response) => this.load(this.glossaryOfRisksService.id)
        );
    }
}

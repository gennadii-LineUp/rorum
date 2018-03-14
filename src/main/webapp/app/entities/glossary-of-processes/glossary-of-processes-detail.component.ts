import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';

import {GlossaryOfProcesses} from './glossary-of-processes.model';
import {GlossaryOfProcessesService} from './glossary-of-processes.service';

@Component({
    selector: 'jhi-glossary-of-processes-detail',
    templateUrl: './glossary-of-processes-detail.component.html'
})
export class GlossaryOfProcessesDetailComponent implements OnInit, OnDestroy {

    glossaryOfProcesses: GlossaryOfProcesses;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private glossaryOfProcessesService: GlossaryOfProcessesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGlossaryOfProcesses();
    }

    load(id) {
        this.glossaryOfProcessesService.find(id).subscribe((glossaryOfProcesses) => {
            this.glossaryOfProcesses = glossaryOfProcesses;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGlossaryOfProcesses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'glossaryOfProcessesListModification',
            (response) => this.load(this.glossaryOfProcesses.id)
        );
    }
}

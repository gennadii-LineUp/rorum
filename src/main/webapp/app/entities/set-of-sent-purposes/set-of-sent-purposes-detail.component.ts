import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';
import 'rxjs/add/operator/takeWhile';

import {SetOfSentPurposes} from './set-of-sent-purposes.model';
import {SetOfSentPurposesService} from './set-of-sent-purposes.service';

@Component({
    selector: 'jhi-set-of-sent-purposes-detail',
    templateUrl: './set-of-sent-purposes-detail.component.html'
})
export class SetOfSentPurposesDetailComponent implements OnInit, OnDestroy {
    setOfSentPurposes: SetOfSentPurposes;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    alive = true;

    constructor(
        private eventManager: JhiEventManager,
        private setOfSentPurposesService: SetOfSentPurposesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id_set_purpose']);
        });
        this.registerChangeInSetOfSentPurposes();
    }

    load(id) {
        this.setOfSentPurposesService.find(id)
            .takeWhile(() => this.alive)
            .subscribe((setOfSentPurposes) => {
            this.setOfSentPurposes = setOfSentPurposes;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
        this.alive = false;
    }

    registerChangeInSetOfSentPurposes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'setOfSentPurposesListModification',
            (response) => this.load(this.setOfSentPurposes.id)
        );
    }
}

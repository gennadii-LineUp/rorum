import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';

import {OrganisationStructure} from './organisation-structure.model';
import {OrganisationStructureService} from './organisation-structure.service';

@Component({
    selector: 'jhi-organisation-structure-detail',
    templateUrl: './organisation-structure-detail.component.html'
})
export class OrganisationStructureDetailComponent implements OnInit, OnDestroy {

    organisationStructure: OrganisationStructure;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private organisationStructureService: OrganisationStructureService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrganisationStructures();
    }

    load(id) {
        this.organisationStructureService.find(id).subscribe((organisationStructure) => {
            this.organisationStructure = organisationStructure;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrganisationStructures() {
        this.eventSubscriber = this.eventManager.subscribe(
            'organisationStructureListModification',
            (response) => this.load(this.organisationStructure.id)
        );
    }
}

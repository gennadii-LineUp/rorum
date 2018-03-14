import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Incident } from './incident.model';
import { IncidentService } from './incident.service';

@Component({
    selector: 'jhi-incident-detail',
    templateUrl: './incident-detail.component.html'
})
export class IncidentDetailComponent implements OnInit, OnDestroy {

    incident: Incident;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private incidentService: IncidentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIncidents();
    }

    load(id) {
        this.incidentService.find(id).subscribe((incident) => {
            this.incident = incident;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIncidents() {
        this.eventSubscriber = this.eventManager.subscribe(
            'incidentListModification',
            (response) => this.load(this.incident.id)
        );
    }
}

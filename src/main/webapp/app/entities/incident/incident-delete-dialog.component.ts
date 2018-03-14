import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Incident } from './incident.model';
import { IncidentPopupService } from './incident-popup.service';
import { IncidentService } from './incident.service';

@Component({
    selector: 'jhi-incident-delete-dialog',
    templateUrl: './incident-delete-dialog.component.html'
})
export class IncidentDeleteDialogComponent {

    incident: Incident;

    constructor(
        private incidentService: IncidentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.incidentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'incidentListModification',
                content: 'Deleted an incident'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-incident-delete-popup',
    template: ''
})
export class IncidentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private incidentPopupService: IncidentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.incidentPopupService
                .open(IncidentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {FilledCommercialRisks} from './filled-commercial-risks.model';
import {FilledCommercialRisksPopupService} from './filled-commercial-risks-popup.service';
import {FilledCommercialRisksService} from './filled-commercial-risks.service';

@Component({
    selector: 'jhi-filled-commercial-risks-delete-dialog',
    templateUrl: './filled-commercial-risks-delete-dialog.component.html'
})
export class FilledCommercialRisksDeleteDialogComponent {

    filledCommercialRisks: FilledCommercialRisks;

    constructor(
        private filledCommercialRisksService: FilledCommercialRisksService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.filledCommercialRisksService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'filledCommercialRisksListModification',
                content: 'Deleted an filledCommercialRisks'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-filled-commercial-risks-delete-popup',
    template: ''
})
export class FilledCommercialRisksDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private filledCommercialRisksPopupService: FilledCommercialRisksPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.filledCommercialRisksPopupService
                .open(FilledCommercialRisksDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

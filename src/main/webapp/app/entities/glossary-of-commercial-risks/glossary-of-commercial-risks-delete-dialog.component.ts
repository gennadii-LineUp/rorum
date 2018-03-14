import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {GlossaryOfCommercialRisks} from './glossary-of-commercial-risks.model';
import {GlossaryOfCommercialRisksPopupService} from './glossary-of-commercial-risks-popup.service';
import {GlossaryOfCommercialRisksService} from './glossary-of-commercial-risks.service';

@Component({
    selector: 'jhi-glossary-of-commercial-risks-delete-dialog',
    templateUrl: './glossary-of-commercial-risks-delete-dialog.component.html'
})
export class GlossaryOfCommercialRisksDeleteDialogComponent {

    glossaryOfCommercialRisksDTO: GlossaryOfCommercialRisks;

    constructor(
        private glossaryOfCommercialRisksService: GlossaryOfCommercialRisksService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.glossaryOfCommercialRisksService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'glossaryOfCommercialRisksListModification',
                content: 'Deleted an glossaryOfCommercialRisksDTO'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-glossary-of-commercial-risks-delete-popup',
    template: ''
})
export class GlossaryOfCommercialRisksDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private glossaryOfCommercialRisksPopupService: GlossaryOfCommercialRisksPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.glossaryOfCommercialRisksPopupService
                .open(GlossaryOfCommercialRisksDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

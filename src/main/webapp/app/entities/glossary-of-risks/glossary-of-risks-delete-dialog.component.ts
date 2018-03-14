import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {GlossaryOfRisks} from './glossary-of-risks.model';
import {GlossaryOfRisksPopupService} from './glossary-of-risks-popup.service';
import {GlossaryOfRisksService} from './glossary-of-risks.service';

@Component({
    selector: 'jhi-glossary-of-risks-delete-dialog',
    templateUrl: './glossary-of-risks-delete-dialog.component.html'
})
export class GlossaryOfRisksDeleteDialogComponent {

    glossaryOfRisksDTO: GlossaryOfRisks;

    constructor(
        private glossaryOfRisksService: GlossaryOfRisksService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.glossaryOfRisksService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'glossaryOfRisksListModification',
                content: 'Deleted an glossaryOfRisksDTO'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-glossary-of-risks-delete-popup',
    template: ''
})
export class GlossaryOfRisksDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private glossaryOfRisksPopupService: GlossaryOfRisksPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.glossaryOfRisksPopupService
                .open(GlossaryOfRisksDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

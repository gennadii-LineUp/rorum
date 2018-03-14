import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {FilledRisks} from './filled-risks.model';
import {FilledRisksPopupService} from './filled-risks-popup.service';
import {FilledRisksService} from './filled-risks.service';

@Component({
    selector: 'jhi-filled-risks-delete-dialog',
    templateUrl: './filled-risks-delete-dialog.component.html'
})
export class FilledRisksDeleteDialogComponent {

    filledRisks: FilledRisks;

    constructor(
        private filledRisksService: FilledRisksService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.filledRisksService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'filledRisksListModification',
                content: 'Deleted an filledRisks'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-filled-risks-delete-popup',
    template: ''
})
export class FilledRisksDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private filledRisksPopupService: FilledRisksPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.filledRisksPopupService
                .open(FilledRisksDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

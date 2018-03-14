import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {FilledMeasureUnits} from './filled-measure-units.model';
import {FilledMeasureUnitsPopupService} from './filled-measure-units-popup.service';
import {FilledMeasureUnitsService} from './filled-measure-units.service';

@Component({
    selector: 'jhi-filled-measure-units-delete-dialog',
    templateUrl: './filled-measure-units-delete-dialog.component.html'
})
export class FilledMeasureUnitsDeleteDialogComponent {

    filledMeasureUnits: FilledMeasureUnits;

    constructor(
        private filledMeasureUnitsService: FilledMeasureUnitsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.filledMeasureUnitsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'filledMeasureUnitsListModification',
                content: 'Deleted an filledMeasureUnits'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-filled-measure-units-delete-popup',
    template: ''
})
export class FilledMeasureUnitsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private filledMeasureUnitsPopupService: FilledMeasureUnitsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.filledMeasureUnitsPopupService
                .open(FilledMeasureUnitsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

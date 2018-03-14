import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {GlossaryOfMeasureUnits} from './glossary-of-measure-units.model';
import {GlossaryOfMeasureUnitsPopupService} from './glossary-of-measure-units-popup.service';
import {GlossaryOfMeasureUnitsService} from './glossary-of-measure-units.service';

@Component({
    selector: 'jhi-glossary-of-measure-units-delete-dialog',
    templateUrl: './glossary-of-measure-units-delete-dialog.component.html'
})
export class GlossaryOfMeasureUnitsDeleteDialogComponent {

    glossaryOfMeasureUnitsDTO: GlossaryOfMeasureUnits;

    constructor(
        private glossaryOfMeasureUnitsService: GlossaryOfMeasureUnitsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.glossaryOfMeasureUnitsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'glossaryOfMeasureUnitsListModification',
                content: 'Deleted an glossaryOfMeasureUnitsDTO'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-glossary-of-measure-units-delete-popup',
    template: ''
})
export class GlossaryOfMeasureUnitsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private glossaryOfMeasureUnitsPopupService: GlossaryOfMeasureUnitsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.glossaryOfMeasureUnitsPopupService
                .open(GlossaryOfMeasureUnitsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

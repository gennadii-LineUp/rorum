import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {MeasureUnitsPurposes} from './measure-units-purposes.model';
import {MeasureUnitsPurposesPopupService} from './measure-units-purposes-popup.service';
import {MeasureUnitsPurposesService} from './measure-units-purposes.service';

@Component({
    selector: 'jhi-measure-units-purposes-delete-dialog',
    templateUrl: './measure-units-purposes-delete-dialog.component.html'
})
export class MeasureUnitsPurposesDeleteDialogComponent {

    measureUnitsPurposes: MeasureUnitsPurposes;

    constructor(
        private measureUnitsPurposesService: MeasureUnitsPurposesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.measureUnitsPurposesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'measureUnitsPurposesListModification',
                content: 'Deleted an measureUnitsPurposes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-measure-units-purposes-delete-popup',
    template: ''
})
export class MeasureUnitsPurposesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private measureUnitsPurposesPopupService: MeasureUnitsPurposesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.measureUnitsPurposesPopupService
                .open(MeasureUnitsPurposesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {FilledMeasureUnits} from './filled-measure-units.model';
import {FilledMeasureUnitsPopupService} from './filled-measure-units-popup.service';
import {FilledMeasureUnitsService} from './filled-measure-units.service';
import {GlossaryOfPurposes, GlossaryOfPurposesService} from '../glossary-of-purposes';
import {MeasureUnitsPurposes, MeasureUnitsPurposesService} from '../measure-units-purposes';
import {GlossaryOfMeasureUnits, GlossaryOfMeasureUnitsService} from '../glossary-of-measure-units';
import {ResponseWrapper} from '../../shared';

@Component({
    selector: 'jhi-filled-measure-units-dialog',
    templateUrl: './filled-measure-units-dialog.component.html'
})
export class FilledMeasureUnitsDialogComponent implements OnInit {

    filledMeasureUnits: FilledMeasureUnits;
    isSaving: boolean;

    glossaryofpurposes: GlossaryOfPurposes[];

    measureunitspurposes: MeasureUnitsPurposes[];

    glossaryofmeasureunits: GlossaryOfMeasureUnits[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private filledMeasureUnitsService: FilledMeasureUnitsService,
        private glossaryOfPurposesService: GlossaryOfPurposesService,
        private measureUnitsPurposesService: MeasureUnitsPurposesService,
        private glossaryOfMeasureUnitsService: GlossaryOfMeasureUnitsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.glossaryOfPurposesService.query()
            .subscribe((res: ResponseWrapper) => { this.glossaryofpurposes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.measureUnitsPurposesService.query()
            .subscribe((res: ResponseWrapper) => { this.measureunitspurposes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.glossaryOfMeasureUnitsService.query()
            .subscribe((res: ResponseWrapper) => { this.glossaryofmeasureunits = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.filledMeasureUnits.id !== undefined) {
            this.subscribeToSaveResponse(
                this.filledMeasureUnitsService.update(this.filledMeasureUnits));
        } else {
            this.subscribeToSaveResponse(
                this.filledMeasureUnitsService.create(this.filledMeasureUnits));
        }
    }

    private subscribeToSaveResponse(result: Observable<FilledMeasureUnits>) {
        result.subscribe((res: FilledMeasureUnits) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: FilledMeasureUnits) {
        this.eventManager.broadcast({ name: 'filledMeasureUnitsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackGlossaryOfPurposesById(index: number, item: GlossaryOfPurposes) {
        return item.id;
    }

    trackMeasureUnitsPurposesById(index: number, item: MeasureUnitsPurposes) {
        return item.id;
    }

    trackGlossaryOfMeasureUnitsById(index: number, item: GlossaryOfMeasureUnits) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-filled-measure-units-popup',
    template: ''
})
export class FilledMeasureUnitsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private filledMeasureUnitsPopupService: FilledMeasureUnitsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.filledMeasureUnitsPopupService
                    .open(FilledMeasureUnitsDialogComponent as Component, params['id']);
            } else {
                this.filledMeasureUnitsPopupService
                    .open(FilledMeasureUnitsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

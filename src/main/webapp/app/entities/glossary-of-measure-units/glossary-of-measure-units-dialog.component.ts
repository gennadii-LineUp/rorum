import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {GlossaryOfMeasureUnits} from './glossary-of-measure-units.model';
import {GlossaryOfMeasureUnitsPopupService} from './glossary-of-measure-units-popup.service';
import {GlossaryOfMeasureUnitsService} from './glossary-of-measure-units.service';
import {GlossaryOfPurposes, GlossaryOfPurposesService} from '../glossary-of-purposes';
import {OrganisationStructure, OrganisationStructureService} from '../organisation-structure';
import {ResponseWrapper} from '../../shared';

@Component({
    selector: 'jhi-glossary-of-measure-units-dialog',
    templateUrl: './glossary-of-measure-units-dialog.component.html'
})
export class GlossaryOfMeasureUnitsDialogComponent implements OnInit {

    glossaryOfMeasureUnitsDTO: GlossaryOfMeasureUnits;
    isSaving: boolean;

    glossaryofpurposes: GlossaryOfPurposes[];

    organisationstructures: OrganisationStructure[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private glossaryOfMeasureUnitsService: GlossaryOfMeasureUnitsService,
        private glossaryOfPurposesService: GlossaryOfPurposesService,
        private organisationStructureService: OrganisationStructureService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.glossaryOfPurposesService.query()
            .subscribe((res: ResponseWrapper) => { this.glossaryofpurposes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.organisationStructureService.query()
            .subscribe((res: ResponseWrapper) => { this.organisationstructures = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.glossaryOfMeasureUnitsDTO.id !== undefined) {
            this.subscribeToSaveResponse(
                this.glossaryOfMeasureUnitsService.update(this.glossaryOfMeasureUnitsDTO));
        } else {
            this.subscribeToSaveResponse(
                this.glossaryOfMeasureUnitsService.create(this.glossaryOfMeasureUnitsDTO));
        }
    }

    private subscribeToSaveResponse(result: Observable<GlossaryOfMeasureUnits>) {
        result.subscribe((res: GlossaryOfMeasureUnits) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: GlossaryOfMeasureUnits) {
        this.eventManager.broadcast({ name: 'glossaryOfMeasureUnitsListModification', content: 'OK'});
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

    trackOrganisationStructureById(index: number, item: OrganisationStructure) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-glossary-of-measure-units-popup',
    template: ''
})
export class GlossaryOfMeasureUnitsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private glossaryOfMeasureUnitsPopupService: GlossaryOfMeasureUnitsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.glossaryOfMeasureUnitsPopupService
                    .open(GlossaryOfMeasureUnitsDialogComponent as Component, params['id']);
            } else {
                this.glossaryOfMeasureUnitsPopupService
                    .open(GlossaryOfMeasureUnitsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

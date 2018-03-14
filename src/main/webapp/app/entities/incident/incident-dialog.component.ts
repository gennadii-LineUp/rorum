import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Incident } from './incident.model';
import { IncidentPopupService } from './incident-popup.service';
import { IncidentService } from './incident.service';
import { SetOfSentPurposes, SetOfSentPurposesService } from '../set-of-sent-purposes';
import { GlossaryOfPurposes, GlossaryOfPurposesService } from '../glossary-of-purposes';
import { FilledRisks, FilledRisksService } from '../filled-risks';
import { FilledCommercialRisks, FilledCommercialRisksService } from '../filled-commercial-risks';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-incident-dialog',
    templateUrl: './incident-dialog.component.html'
})
export class IncidentDialogComponent implements OnInit {

    incident: Incident;
    isSaving: boolean;

    setofsentpurposes: SetOfSentPurposes[];

    glossaryofpurposes: GlossaryOfPurposes[];

    filledrisks: FilledRisks[];

    filledcommercialrisks: FilledCommercialRisks[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private incidentService: IncidentService,
        private setOfSentPurposesService: SetOfSentPurposesService,
        private glossaryOfPurposesService: GlossaryOfPurposesService,
        private filledRisksService: FilledRisksService,
        private filledCommercialRisksService: FilledCommercialRisksService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.setOfSentPurposesService.query()
            .subscribe((res: ResponseWrapper) => { this.setofsentpurposes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.glossaryOfPurposesService.query()
            .subscribe((res: ResponseWrapper) => { this.glossaryofpurposes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.filledRisksService.query()
            .subscribe((res: ResponseWrapper) => { this.filledrisks = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.filledCommercialRisksService.query()
            .subscribe((res: ResponseWrapper) => { this.filledcommercialrisks = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.incident.id !== undefined) {
            this.subscribeToSaveResponse(
                this.incidentService.update(this.incident));
        } else {
            this.subscribeToSaveResponse(
                this.incidentService.create(this.incident));
        }
    }

    private subscribeToSaveResponse(result: Observable<Incident>) {
        result.subscribe((res: Incident) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Incident) {
        this.eventManager.broadcast({ name: 'incidentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSetOfSentPurposesById(index: number, item: SetOfSentPurposes) {
        return item.id;
    }

    trackGlossaryOfPurposesById(index: number, item: GlossaryOfPurposes) {
        return item.id;
    }

    trackFilledRisksById(index: number, item: FilledRisks) {
        return item.id;
    }

    trackFilledCommercialRisksById(index: number, item: FilledCommercialRisks) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-incident-popup',
    template: ''
})
export class IncidentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private incidentPopupService: IncidentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.incidentPopupService
                    .open(IncidentDialogComponent as Component, params['id']);
            } else {
                this.incidentPopupService
                    .open(IncidentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

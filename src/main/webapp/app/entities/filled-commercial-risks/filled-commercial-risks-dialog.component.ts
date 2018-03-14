import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {FilledCommercialRisks} from './filled-commercial-risks.model';
import {FilledCommercialRisksPopupService} from './filled-commercial-risks-popup.service';
import {FilledCommercialRisksService} from './filled-commercial-risks.service';
import {GlossaryOfCommercialRisks, GlossaryOfCommercialRisksService} from '../glossary-of-commercial-risks';
import {CommercialRisksPurposes, CommercialRisksPurposesService} from '../commercial-risks-purposes';
import {ResponseWrapper} from '../../shared';

@Component({
    selector: 'jhi-filled-commercial-risks-dialog',
    templateUrl: './filled-commercial-risks-dialog.component.html'
})
export class FilledCommercialRisksDialogComponent implements OnInit {

    filledCommercialRisks: FilledCommercialRisks;
    isSaving: boolean;

    glossaryofcommercialrisks: GlossaryOfCommercialRisks[];

    commercialriskspurposes: CommercialRisksPurposes[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private filledCommercialRisksService: FilledCommercialRisksService,
        private glossaryOfCommercialRisksService: GlossaryOfCommercialRisksService,
        private commercialRisksPurposesService: CommercialRisksPurposesService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.glossaryOfCommercialRisksService.query()
            .subscribe((res: ResponseWrapper) => { this.glossaryofcommercialrisks = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.commercialRisksPurposesService.query()
            .subscribe((res: ResponseWrapper) => { this.commercialriskspurposes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.filledCommercialRisks.id !== undefined) {
            this.subscribeToSaveResponse(
                this.filledCommercialRisksService.update(this.filledCommercialRisks));
        } else {
            this.subscribeToSaveResponse(
                this.filledCommercialRisksService.create(this.filledCommercialRisks));
        }
    }

    private subscribeToSaveResponse(result: Observable<FilledCommercialRisks>) {
        result.subscribe((res: FilledCommercialRisks) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: FilledCommercialRisks) {
        this.eventManager.broadcast({ name: 'filledCommercialRisksListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackGlossaryOfCommercialRisksById(index: number, item: GlossaryOfCommercialRisks) {
        return item.id;
    }

    trackCommercialRisksPurposesById(index: number, item: CommercialRisksPurposes) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-filled-commercial-risks-popup',
    template: ''
})
export class FilledCommercialRisksPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private filledCommercialRisksPopupService: FilledCommercialRisksPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.filledCommercialRisksPopupService
                    .open(FilledCommercialRisksDialogComponent as Component, params['id']);
            } else {
                this.filledCommercialRisksPopupService
                    .open(FilledCommercialRisksDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

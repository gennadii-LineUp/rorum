import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {FilledRisks} from './filled-risks.model';
import {FilledRisksPopupService} from './filled-risks-popup.service';
import {FilledRisksService} from './filled-risks.service';
import {GlossaryOfPurposes, GlossaryOfPurposesService} from '../glossary-of-purposes';
import {RisksPurposes, RisksPurposesService} from '../risks-purposes';
import {GlossaryOfRisks, GlossaryOfRisksService} from '../glossary-of-risks';
import {ResponseWrapper} from '../../shared';

@Component({
    selector: 'jhi-filled-risks-dialog',
    templateUrl: './filled-risks-dialog.component.html'
})
export class FilledRisksDialogComponent implements OnInit {

    filledRisks: FilledRisks;
    isSaving: boolean;

    glossaryofpurposes: GlossaryOfPurposes[];

    riskspurposes: RisksPurposes[];

    glossaryofrisks: GlossaryOfRisks[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private filledRisksService: FilledRisksService,
        private glossaryOfPurposesService: GlossaryOfPurposesService,
        private risksPurposesService: RisksPurposesService,
        private glossaryOfRisksService: GlossaryOfRisksService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.glossaryOfPurposesService.query()
            .subscribe((res: ResponseWrapper) => { this.glossaryofpurposes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.risksPurposesService.query()
            .subscribe((res: ResponseWrapper) => { this.riskspurposes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.glossaryOfRisksService.query()
            .subscribe((res: ResponseWrapper) => { this.glossaryofrisks = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.filledRisks.id !== undefined) {
            this.subscribeToSaveResponse(
                this.filledRisksService.update(this.filledRisks));
        } else {
            this.subscribeToSaveResponse(
                this.filledRisksService.create(this.filledRisks));
        }
    }

    private subscribeToSaveResponse(result: Observable<FilledRisks>) {
        result.subscribe((res: FilledRisks) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: FilledRisks) {
        this.eventManager.broadcast({ name: 'filledRisksListModification', content: 'OK'});
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

    trackRisksPurposesById(index: number, item: RisksPurposes) {
        return item.id;
    }

    trackGlossaryOfRisksById(index: number, item: GlossaryOfRisks) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-filled-risks-popup',
    template: ''
})
export class FilledRisksPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private filledRisksPopupService: FilledRisksPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.filledRisksPopupService
                    .open(FilledRisksDialogComponent as Component, params['id']);
            } else {
                this.filledRisksPopupService
                    .open(FilledRisksDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

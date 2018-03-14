import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HighCommercialRisk } from './high-commercial-risk.model';
import { HighCommercialRiskPopupService } from './high-commercial-risk-popup.service';
import { HighCommercialRiskService } from './high-commercial-risk.service';
import { FilledCommercialRisks, FilledCommercialRisksService } from '../filled-commercial-risks';
import { DecisionForRisk, DecisionForRiskService } from '../decision-for-risk';
import { PossibilitiesToImproveRisk, PossibilitiesToImproveRiskService } from '../possibilities-to-improve-risk';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-high-commercial-risk-dialog',
    templateUrl: './high-commercial-risk-dialog.component.html'
})
export class HighCommercialRiskDialogComponent implements OnInit {

    highCommercialRisk: HighCommercialRisk;
    isSaving: boolean;

    filledcommercialrisks: FilledCommercialRisks[];

    decisionforrisks: DecisionForRisk[];

    possibilitiestoimproverisks: PossibilitiesToImproveRisk[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private highCommercialRiskService: HighCommercialRiskService,
        private filledCommercialRisksService: FilledCommercialRisksService,
        private decisionForRiskService: DecisionForRiskService,
        private possibilitiesToImproveRiskService: PossibilitiesToImproveRiskService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.filledCommercialRisksService
            .query({filter: 'highcommercialrisk-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.highCommercialRisk.filledCommercialRisks || !this.highCommercialRisk.filledCommercialRisks.id) {
                    this.filledcommercialrisks = res.json;
                } else {
                    this.filledCommercialRisksService
                        .find(this.highCommercialRisk.filledCommercialRisks.id)
                        .subscribe((subRes: FilledCommercialRisks) => {
                            this.filledcommercialrisks = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.decisionForRiskService
            .query({filter: 'highcommercialrisk-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.highCommercialRisk.decisionForRisk || !this.highCommercialRisk.decisionForRisk.id) {
                    this.decisionforrisks = res.json;
                } else {
                    this.decisionForRiskService
                        .find(this.highCommercialRisk.decisionForRisk.id)
                        .subscribe((subRes: DecisionForRisk) => {
                            this.decisionforrisks = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.possibilitiesToImproveRiskService.query()
            .subscribe((res: ResponseWrapper) => { this.possibilitiestoimproverisks = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.highCommercialRisk.id !== undefined) {
            this.subscribeToSaveResponse(
                this.highCommercialRiskService.update(this.highCommercialRisk));
        } else {
            this.subscribeToSaveResponse(
                this.highCommercialRiskService.create(this.highCommercialRisk));
        }
    }

    private subscribeToSaveResponse(result: Observable<HighCommercialRisk>) {
        result.subscribe((res: HighCommercialRisk) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: HighCommercialRisk) {
        this.eventManager.broadcast({ name: 'highCommercialRiskListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackFilledCommercialRisksById(index: number, item: FilledCommercialRisks) {
        return item.id;
    }

    trackDecisionForRiskById(index: number, item: DecisionForRisk) {
        return item.id;
    }

    trackPossibilitiesToImproveRiskById(index: number, item: PossibilitiesToImproveRisk) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-high-commercial-risk-popup',
    template: ''
})
export class HighCommercialRiskPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private highCommercialRiskPopupService: HighCommercialRiskPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.highCommercialRiskPopupService
                    .open(HighCommercialRiskDialogComponent as Component, params['id']);
            } else {
                this.highCommercialRiskPopupService
                    .open(HighCommercialRiskDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

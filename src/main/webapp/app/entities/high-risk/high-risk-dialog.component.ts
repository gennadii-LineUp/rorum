import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {HighRisk} from './high-risk.model';
import {HighRiskPopupService} from './high-risk-popup.service';
import {HighRiskService} from './high-risk.service';
import {FilledRisks, FilledRisksService} from '../filled-risks';
import {DecisionForRisk, DecisionForRiskService} from '../decision-for-risk';
import {PossibilitiesToImproveRisk, PossibilitiesToImproveRiskService} from '../possibilities-to-improve-risk';
import {ResponseWrapper} from '../../shared';

@Component({
    selector: 'jhi-high-risk-dialog',
    templateUrl: './high-risk-dialog.component.html'
})
export class HighRiskDialogComponent implements OnInit {

    highRisk: HighRisk;
    isSaving: boolean;

    filledrisks: FilledRisks[];

    decisionforrisks: DecisionForRisk[];

    possibilitiestoimproverisks: PossibilitiesToImproveRisk[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private highRiskService: HighRiskService,
        private filledRisksService: FilledRisksService,
        private decisionForRiskService: DecisionForRiskService,
        private possibilitiesToImproveRiskService: PossibilitiesToImproveRiskService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.filledRisksService.query()
            .subscribe((res: ResponseWrapper) => { this.filledrisks = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.decisionForRiskService
            .query({filter: 'highrisk-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.highRisk.decisionForRisk || !this.highRisk.decisionForRisk.id) {
                    this.decisionforrisks = res.json;
                } else {
                    this.decisionForRiskService
                        .find(this.highRisk.decisionForRisk.id)
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
        if (this.highRisk.id !== undefined) {
            this.subscribeToSaveResponse(
                this.highRiskService.update(this.highRisk));
        } else {
            this.subscribeToSaveResponse(
                this.highRiskService.create(this.highRisk));
        }
    }

    private subscribeToSaveResponse(result: Observable<HighRisk>) {
        result.subscribe((res: HighRisk) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: HighRisk) {
        this.eventManager.broadcast({ name: 'highRiskListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackFilledRisksById(index: number, item: FilledRisks) {
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
    selector: 'jhi-high-risk-popup',
    template: ''
})
export class HighRiskPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private highRiskPopupService: HighRiskPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.highRiskPopupService
                    .open(HighRiskDialogComponent as Component, params['id']);
            } else {
                this.highRiskPopupService
                    .open(HighRiskDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

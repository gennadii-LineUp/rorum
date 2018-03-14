import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {DecisionForRisk} from './decision-for-risk.model';
import {DecisionForRiskPopupService} from './decision-for-risk-popup.service';
import {DecisionForRiskService} from './decision-for-risk.service';

@Component({
    selector: 'jhi-decision-for-risk-dialog',
    templateUrl: './decision-for-risk-dialog.component.html'
})
export class DecisionForRiskDialogComponent implements OnInit {

    decisionForRisk: DecisionForRisk;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private decisionForRiskService: DecisionForRiskService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.decisionForRisk.id !== undefined) {
            this.subscribeToSaveResponse(
                this.decisionForRiskService.update(this.decisionForRisk));
        } else {
            this.subscribeToSaveResponse(
                this.decisionForRiskService.create(this.decisionForRisk));
        }
    }

    private subscribeToSaveResponse(result: Observable<DecisionForRisk>) {
        result.subscribe((res: DecisionForRisk) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: DecisionForRisk) {
        this.eventManager.broadcast({ name: 'decisionForRiskListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-decision-for-risk-popup',
    template: ''
})
export class DecisionForRiskPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private decisionForRiskPopupService: DecisionForRiskPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.decisionForRiskPopupService
                    .open(DecisionForRiskDialogComponent as Component, params['id']);
            } else {
                this.decisionForRiskPopupService
                    .open(DecisionForRiskDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

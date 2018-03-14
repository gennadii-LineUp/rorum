import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {PossibilitiesToImproveRisk} from './possibilities-to-improve-risk.model';
import {PossibilitiesToImproveRiskPopupService} from './possibilities-to-improve-risk-popup.service';
import {PossibilitiesToImproveRiskService} from './possibilities-to-improve-risk.service';

@Component({
    selector: 'jhi-possibilities-to-improve-risk-dialog',
    templateUrl: './possibilities-to-improve-risk-dialog.component.html'
})
export class PossibilitiesToImproveRiskDialogComponent implements OnInit {

    possibilitiesToImproveRisk: PossibilitiesToImproveRisk;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private possibilitiesToImproveRiskService: PossibilitiesToImproveRiskService,
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
        if (this.possibilitiesToImproveRisk.id !== undefined) {
            this.subscribeToSaveResponse(
                this.possibilitiesToImproveRiskService.update(this.possibilitiesToImproveRisk));
        } else {
            this.subscribeToSaveResponse(
                this.possibilitiesToImproveRiskService.create(this.possibilitiesToImproveRisk));
        }
    }

    private subscribeToSaveResponse(result: Observable<PossibilitiesToImproveRisk>) {
        result.subscribe((res: PossibilitiesToImproveRisk) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PossibilitiesToImproveRisk) {
        this.eventManager.broadcast({ name: 'possibilitiesToImproveRiskListModification', content: 'OK'});
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
    selector: 'jhi-possibilities-to-improve-risk-popup',
    template: ''
})
export class PossibilitiesToImproveRiskPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private possibilitiesToImproveRiskPopupService: PossibilitiesToImproveRiskPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.possibilitiesToImproveRiskPopupService
                    .open(PossibilitiesToImproveRiskDialogComponent as Component, params['id']);
            } else {
                this.possibilitiesToImproveRiskPopupService
                    .open(PossibilitiesToImproveRiskDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

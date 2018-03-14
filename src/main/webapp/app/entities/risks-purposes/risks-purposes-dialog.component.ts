import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {RisksPurposes} from './risks-purposes.model';
import {RisksPurposesPopupService} from './risks-purposes-popup.service';
import {RisksPurposesService} from './risks-purposes.service';
import {SetOfSentPurposes, SetOfSentPurposesService} from '../set-of-sent-purposes';
import {ResponseWrapper} from '../../shared';

@Component({
    selector: 'jhi-risks-purposes-dialog',
    templateUrl: './risks-purposes-dialog.component.html'
})
export class RisksPurposesDialogComponent implements OnInit {

    risksPurposes: RisksPurposes;
    isSaving: boolean;

    setofsentpurposes: SetOfSentPurposes[];
    creationDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private risksPurposesService: RisksPurposesService,
        private setOfSentPurposesService: SetOfSentPurposesService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.setOfSentPurposesService.query()
            .subscribe((res: ResponseWrapper) => { this.setofsentpurposes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.risksPurposes.id !== undefined) {
            this.subscribeToSaveResponse(
                this.risksPurposesService.update(this.risksPurposes));
        } else {
            this.subscribeToSaveResponse(
                this.risksPurposesService.create(this.risksPurposes));
        }
    }

    private subscribeToSaveResponse(result: Observable<RisksPurposes>) {
        result.subscribe((res: RisksPurposes) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: RisksPurposes) {
        this.eventManager.broadcast({ name: 'risksPurposesListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-risks-purposes-popup',
    template: ''
})
export class RisksPurposesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private risksPurposesPopupService: RisksPurposesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.risksPurposesPopupService
                    .open(RisksPurposesDialogComponent as Component, params['id']);
            } else {
                this.risksPurposesPopupService
                    .open(RisksPurposesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {CommercialRisksPurposes} from './commercial-risks-purposes.model';
import {CommercialRisksPurposesPopupService} from './commercial-risks-purposes-popup.service';
import {CommercialRisksPurposesService} from './commercial-risks-purposes.service';
import {SetOfSentPurposes, SetOfSentPurposesService} from '../set-of-sent-purposes';
import {ResponseWrapper} from '../../shared';

@Component({
    selector: 'jhi-commercial-risks-purposes-dialog',
    templateUrl: './commercial-risks-purposes-dialog.component.html'
})
export class CommercialRisksPurposesDialogComponent implements OnInit {

    commercialRisksPurposes: CommercialRisksPurposes;
    isSaving: boolean;

    setofsentpurposes: SetOfSentPurposes[];
    creationDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private commercialRisksPurposesService: CommercialRisksPurposesService,
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
        if (this.commercialRisksPurposes.id !== undefined) {
            this.subscribeToSaveResponse(
                this.commercialRisksPurposesService.update(this.commercialRisksPurposes));
        } else {
            this.subscribeToSaveResponse(
                this.commercialRisksPurposesService.create(this.commercialRisksPurposes));
        }
    }

    private subscribeToSaveResponse(result: Observable<CommercialRisksPurposes>) {
        result.subscribe((res: CommercialRisksPurposes) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CommercialRisksPurposes) {
        this.eventManager.broadcast({ name: 'commercialRisksPurposesListModification', content: 'OK'});
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
    selector: 'jhi-commercial-risks-purposes-popup',
    template: ''
})
export class CommercialRisksPurposesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commercialRisksPurposesPopupService: CommercialRisksPurposesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.commercialRisksPurposesPopupService
                    .open(CommercialRisksPurposesDialogComponent as Component, params['id']);
            } else {
                this.commercialRisksPurposesPopupService
                    .open(CommercialRisksPurposesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

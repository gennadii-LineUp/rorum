import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {MeasureUnitsPurposes} from './measure-units-purposes.model';
import {MeasureUnitsPurposesPopupService} from './measure-units-purposes-popup.service';
import {MeasureUnitsPurposesService} from './measure-units-purposes.service';
import {SetOfSentPurposes, SetOfSentPurposesService} from '../set-of-sent-purposes';
import {ResponseWrapper} from '../../shared';

@Component({
    selector: 'jhi-measure-units-purposes-dialog',
    templateUrl: './measure-units-purposes-dialog.component.html'
})
export class MeasureUnitsPurposesDialogComponent implements OnInit {

    measureUnitsPurposes: MeasureUnitsPurposes;
    isSaving: boolean;

    setofsentpurposes: SetOfSentPurposes[];
    creationDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private measureUnitsPurposesService: MeasureUnitsPurposesService,
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
        if (this.measureUnitsPurposes.id !== undefined) {
            this.subscribeToSaveResponse(
                this.measureUnitsPurposesService.update(this.measureUnitsPurposes));
        } else {
            this.subscribeToSaveResponse(
                this.measureUnitsPurposesService.create(this.measureUnitsPurposes));
        }
    }

    private subscribeToSaveResponse(result: Observable<MeasureUnitsPurposes>) {
        result.subscribe((res: MeasureUnitsPurposes) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MeasureUnitsPurposes) {
        this.eventManager.broadcast({ name: 'measureUnitsPurposesListModification', content: 'OK'});
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
    selector: 'jhi-measure-units-purposes-popup',
    template: ''
})
export class MeasureUnitsPurposesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private measureUnitsPurposesPopupService: MeasureUnitsPurposesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.measureUnitsPurposesPopupService
                    .open(MeasureUnitsPurposesDialogComponent as Component, params['id']);
            } else {
                this.measureUnitsPurposesPopupService
                    .open(MeasureUnitsPurposesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {GlossaryOfPurposes} from './glossary-of-purposes.model';
import {GlossaryOfPurposesPopupService} from './glossary-of-purposes-popup.service';
import {GlossaryOfPurposesService} from './glossary-of-purposes.service';
import {GlossaryOfProcesses, GlossaryOfProcessesService} from '../glossary-of-processes';
import {OrganisationStructure, OrganisationStructureService} from '../organisation-structure';
import {ResponseWrapper} from '../../shared';

@Component({
    selector: 'jhi-glossary-of-purposes-dialog',
    templateUrl: './glossary-of-purposes-dialog.component.html'
})
export class GlossaryOfPurposesDialogComponent implements OnInit {

    glossaryOfRisksService: GlossaryOfPurposes;
    isSaving: boolean;

    glossaryofprocesses: GlossaryOfProcesses[];

    organisationstructures: OrganisationStructure[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private glossaryOfPurposesService: GlossaryOfPurposesService,
        private glossaryOfProcessesService: GlossaryOfProcessesService,
        private organisationStructureService: OrganisationStructureService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.glossaryOfProcessesService.query()
            .subscribe((res: ResponseWrapper) => { this.glossaryofprocesses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.organisationStructureService.query()
            .subscribe((res: ResponseWrapper) => { this.organisationstructures = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.glossaryOfRisksService.id !== undefined) {
            this.subscribeToSaveResponse(
                this.glossaryOfPurposesService.update(this.glossaryOfRisksService));
        } else {
            this.subscribeToSaveResponse(
                this.glossaryOfPurposesService.create(this.glossaryOfRisksService));
        }
    }

    private subscribeToSaveResponse(result: Observable<GlossaryOfPurposes>) {
        result.subscribe((res: GlossaryOfPurposes) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: GlossaryOfPurposes) {
        this.eventManager.broadcast({ name: 'glossaryOfPurposesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackGlossaryOfProcessesById(index: number, item: GlossaryOfProcesses) {
        return item.id;
    }

    trackOrganisationStructureById(index: number, item: OrganisationStructure) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-glossary-of-purposes-popup',
    template: ''
})
export class GlossaryOfPurposesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private glossaryOfPurposesPopupService: GlossaryOfPurposesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.glossaryOfPurposesPopupService
                    .open(GlossaryOfPurposesDialogComponent as Component, params['id']);
            } else {
                this.glossaryOfPurposesPopupService
                    .open(GlossaryOfPurposesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

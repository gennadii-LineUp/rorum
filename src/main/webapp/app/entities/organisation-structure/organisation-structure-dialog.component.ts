import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {OrganisationStructure} from './organisation-structure.model';
import {OrganisationStructurePopupService} from './organisation-structure-popup.service';
import {OrganisationStructureService} from './organisation-structure.service';
import {GlossaryOfProcesses, GlossaryOfProcessesService} from '../glossary-of-processes';
import {ResponseWrapper} from '../../shared';

@Component({
    selector: 'jhi-organisation-structure-dialog',
    templateUrl: './organisation-structure-dialog.component.html'
})
export class OrganisationStructureDialogComponent implements OnInit {

    organisationStructure: OrganisationStructure;
    isSaving: boolean;

    glossaryofprocesses: GlossaryOfProcesses[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private organisationStructureService: OrganisationStructureService,
        private glossaryOfProcessesService: GlossaryOfProcessesService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.glossaryOfProcessesService.query()
            .subscribe((res: ResponseWrapper) => { this.glossaryofprocesses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.organisationStructure.id !== undefined) {
            this.subscribeToSaveResponse(
                this.organisationStructureService.update(this.organisationStructure));
        } else {
            this.subscribeToSaveResponse(
                this.organisationStructureService.create(this.organisationStructure));
        }
    }

    private subscribeToSaveResponse(result: Observable<OrganisationStructure>) {
        result.subscribe((res: OrganisationStructure) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OrganisationStructure) {
        this.eventManager.broadcast({ name: 'organisationStructureListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-organisation-structure-popup',
    template: ''
})
export class OrganisationStructurePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private organisationStructurePopupService: OrganisationStructurePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.organisationStructurePopupService
                    .open(OrganisationStructureDialogComponent as Component, params['id']);
            } else {
                this.organisationStructurePopupService
                    .open(OrganisationStructureDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

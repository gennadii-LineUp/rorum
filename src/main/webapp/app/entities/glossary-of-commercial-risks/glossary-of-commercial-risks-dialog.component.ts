import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {GlossaryOfCommercialRisks} from './glossary-of-commercial-risks.model';
import {GlossaryOfCommercialRisksPopupService} from './glossary-of-commercial-risks-popup.service';
import {GlossaryOfCommercialRisksService} from './glossary-of-commercial-risks.service';
import {ResponseWrapper, User, UserService} from '../../shared';
import {OrganisationStructure, OrganisationStructureService} from '../organisation-structure';

@Component({
    selector: 'jhi-glossary-of-commercial-risks-dialog',
    templateUrl: './glossary-of-commercial-risks-dialog.component.html'
})
export class GlossaryOfCommercialRisksDialogComponent implements OnInit {

    glossaryOfCommercialRisksDTO: GlossaryOfCommercialRisks;
    isSaving: boolean;

    users: User[];

    organisationstructures: OrganisationStructure[];
    completionDateDp: any;
    creationDateDp: any;
    importantToDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private glossaryOfCommercialRisksService: GlossaryOfCommercialRisksService,
        private userService: UserService,
        private organisationStructureService: OrganisationStructureService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.organisationStructureService.query()
            .subscribe((res: ResponseWrapper) => { this.organisationstructures = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.glossaryOfCommercialRisksDTO.id !== undefined) {
            this.subscribeToSaveResponse(
                this.glossaryOfCommercialRisksService.update(this.glossaryOfCommercialRisksDTO));
        } else {
            this.subscribeToSaveResponse(
                this.glossaryOfCommercialRisksService.create(this.glossaryOfCommercialRisksDTO));
        }
    }

    private subscribeToSaveResponse(result: Observable<GlossaryOfCommercialRisks>) {
        result.subscribe((res: GlossaryOfCommercialRisks) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: GlossaryOfCommercialRisks) {
        this.eventManager.broadcast({ name: 'glossaryOfCommercialRisksListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackOrganisationStructureById(index: number, item: OrganisationStructure) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-glossary-of-commercial-risks-popup',
    template: ''
})
export class GlossaryOfCommercialRisksPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private glossaryOfCommercialRisksPopupService: GlossaryOfCommercialRisksPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.glossaryOfCommercialRisksPopupService
                    .open(GlossaryOfCommercialRisksDialogComponent as Component, params['id']);
            } else {
                this.glossaryOfCommercialRisksPopupService
                    .open(GlossaryOfCommercialRisksDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

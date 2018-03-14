import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {GlossaryOfRisks} from './glossary-of-risks.model';
import {GlossaryOfRisksPopupService} from './glossary-of-risks-popup.service';
import {GlossaryOfRisksService} from './glossary-of-risks.service';
import {ResponseWrapper, User, UserService} from '../../shared';
import {GlossaryOfPurposes, GlossaryOfPurposesService} from '../glossary-of-purposes';
import {OrganisationStructure, OrganisationStructureService} from '../organisation-structure';

@Component({
    selector: 'jhi-glossary-of-risks-dialog',
    templateUrl: './glossary-of-risks-dialog.component.html'
})
export class GlossaryOfRisksDialogComponent implements OnInit {

    glossaryOfRisksDTO: GlossaryOfRisks;
    isSaving: boolean;

    users: User[];

    glossaryofpurposes: GlossaryOfPurposes[];

    organisationstructures: OrganisationStructure[];
    completionDateDp: any;
    creationDateDp: any;
    importantToDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private glossaryOfRisksService: GlossaryOfRisksService,
        private userService: UserService,
        private glossaryOfPurposesService: GlossaryOfPurposesService,
        private organisationStructureService: OrganisationStructureService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.glossaryOfPurposesService.query()
            .subscribe((res: ResponseWrapper) => { this.glossaryofpurposes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.organisationStructureService.query()
            .subscribe((res: ResponseWrapper) => { this.organisationstructures = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.glossaryOfRisksDTO.id !== undefined) {
            this.subscribeToSaveResponse(
                this.glossaryOfRisksService.update(this.glossaryOfRisksDTO));
        } else {
            this.subscribeToSaveResponse(
                this.glossaryOfRisksService.create(this.glossaryOfRisksDTO));
        }
    }

    private subscribeToSaveResponse(result: Observable<GlossaryOfRisks>) {
        result.subscribe((res: GlossaryOfRisks) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: GlossaryOfRisks) {
        this.eventManager.broadcast({ name: 'glossaryOfRisksListModification', content: 'OK'});
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

    trackGlossaryOfPurposesById(index: number, item: GlossaryOfPurposes) {
        return item.id;
    }

    trackOrganisationStructureById(index: number, item: OrganisationStructure) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-glossary-of-risks-popup',
    template: ''
})
export class GlossaryOfRisksPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private glossaryOfRisksPopupService: GlossaryOfRisksPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.glossaryOfRisksPopupService
                    .open(GlossaryOfRisksDialogComponent as Component, params['id']);
            } else {
                this.glossaryOfRisksPopupService
                    .open(GlossaryOfRisksDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

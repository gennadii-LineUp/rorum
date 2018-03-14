import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {GlossaryOfProcesses} from './glossary-of-processes.model';
import {GlossaryOfProcessesPopupService} from './glossary-of-processes-popup.service';
import {GlossaryOfProcessesService} from './glossary-of-processes.service';

@Component({
    selector: 'jhi-glossary-of-processes-dialog',
    templateUrl: './glossary-of-processes-dialog.component.html'
})
export class GlossaryOfProcessesDialogComponent implements OnInit {

    glossaryOfProcesses: GlossaryOfProcesses;
    isSaving: boolean;
    importantToDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private glossaryOfProcessesService: GlossaryOfProcessesService,
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
        if (this.glossaryOfProcesses.id !== undefined) {
            this.subscribeToSaveResponse(
                this.glossaryOfProcessesService.update(this.glossaryOfProcesses));
        } else {
            this.subscribeToSaveResponse(
                this.glossaryOfProcessesService.create(this.glossaryOfProcesses));
        }
    }

    private subscribeToSaveResponse(result: Observable<GlossaryOfProcesses>) {
        result.subscribe((res: GlossaryOfProcesses) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: GlossaryOfProcesses) {
        this.eventManager.broadcast({ name: 'glossaryOfProcessesListModification', content: 'OK'});
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
    selector: 'jhi-glossary-of-processes-popup',
    template: ''
})
export class GlossaryOfProcessesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private glossaryOfProcessesPopupService: GlossaryOfProcessesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.glossaryOfProcessesPopupService
                    .open(GlossaryOfProcessesDialogComponent as Component, params['id']);
            } else {
                this.glossaryOfProcessesPopupService
                    .open(GlossaryOfProcessesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

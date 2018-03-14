import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {GlossaryOfProcesses} from './glossary-of-processes.model';
import {GlossaryOfProcessesPopupService} from './glossary-of-processes-popup.service';
import {GlossaryOfProcessesService} from './glossary-of-processes.service';

@Component({
    selector: 'jhi-glossary-of-processes-delete-dialog',
    templateUrl: './glossary-of-processes-delete-dialog.component.html'
})
export class GlossaryOfProcessesDeleteDialogComponent {

    glossaryOfProcesses: GlossaryOfProcesses;

    constructor(
        private glossaryOfProcessesService: GlossaryOfProcessesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.glossaryOfProcessesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'glossaryOfProcessesListModification',
                content: 'Deleted an glossaryOfProcesses'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-glossary-of-processes-delete-popup',
    template: ''
})
export class GlossaryOfProcessesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private glossaryOfProcessesPopupService: GlossaryOfProcessesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.glossaryOfProcessesPopupService
                .open(GlossaryOfProcessesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

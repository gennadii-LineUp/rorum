import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {GlossaryOfPurposes} from './glossary-of-purposes.model';
import {GlossaryOfPurposesPopupService} from './glossary-of-purposes-popup.service';
import {GlossaryOfPurposesService} from './glossary-of-purposes.service';

@Component({
    selector: 'jhi-glossary-of-purposes-delete-dialog',
    templateUrl: './glossary-of-purposes-delete-dialog.component.html'
})
export class GlossaryOfPurposesDeleteDialogComponent {

    glossaryOfRisksService: GlossaryOfPurposes;

    constructor(
        private glossaryOfPurposesService: GlossaryOfPurposesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.glossaryOfPurposesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'glossaryOfPurposesListModification',
                content: 'Deleted an glossaryOfPurposes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-glossary-of-purposes-delete-popup',
    template: ''
})
export class GlossaryOfPurposesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private glossaryOfPurposesPopupService: GlossaryOfPurposesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.glossaryOfPurposesPopupService
                .open(GlossaryOfPurposesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

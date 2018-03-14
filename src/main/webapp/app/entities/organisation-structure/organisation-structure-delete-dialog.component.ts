import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {OrganisationStructure} from './organisation-structure.model';
import {OrganisationStructurePopupService} from './organisation-structure-popup.service';
import {OrganisationStructureService} from './organisation-structure.service';

@Component({
    selector: 'jhi-organisation-structure-delete-dialog',
    templateUrl: './organisation-structure-delete-dialog.component.html'
})
export class OrganisationStructureDeleteDialogComponent {

    organisationStructure: OrganisationStructure;

    constructor(
        private organisationStructureService: OrganisationStructureService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.organisationStructureService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'organisationStructureListModification',
                content: 'Deleted an organisationStructure'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-organisation-structure-delete-popup',
    template: ''
})
export class OrganisationStructureDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private organisationStructurePopupService: OrganisationStructurePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.organisationStructurePopupService
                .open(OrganisationStructureDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

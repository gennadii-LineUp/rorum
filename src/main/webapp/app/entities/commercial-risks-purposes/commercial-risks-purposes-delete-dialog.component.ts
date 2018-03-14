import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {CommercialRisksPurposes} from './commercial-risks-purposes.model';
import {CommercialRisksPurposesPopupService} from './commercial-risks-purposes-popup.service';
import {CommercialRisksPurposesService} from './commercial-risks-purposes.service';

@Component({
    selector: 'jhi-commercial-risks-purposes-delete-dialog',
    templateUrl: './commercial-risks-purposes-delete-dialog.component.html'
})
export class CommercialRisksPurposesDeleteDialogComponent {

    commercialRisksPurposes: CommercialRisksPurposes;

    constructor(
        private commercialRisksPurposesService: CommercialRisksPurposesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commercialRisksPurposesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'commercialRisksPurposesListModification',
                content: 'Deleted an commercialRisksPurposes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commercial-risks-purposes-delete-popup',
    template: ''
})
export class CommercialRisksPurposesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commercialRisksPurposesPopupService: CommercialRisksPurposesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.commercialRisksPurposesPopupService
                .open(CommercialRisksPurposesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

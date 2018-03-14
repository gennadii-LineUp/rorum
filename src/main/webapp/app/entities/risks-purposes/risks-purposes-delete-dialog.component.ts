import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {RisksPurposes} from './risks-purposes.model';
import {RisksPurposesPopupService} from './risks-purposes-popup.service';
import {RisksPurposesService} from './risks-purposes.service';

@Component({
    selector: 'jhi-risks-purposes-delete-dialog',
    templateUrl: './risks-purposes-delete-dialog.component.html'
})
export class RisksPurposesDeleteDialogComponent {

    risksPurposes: RisksPurposes;

    constructor(
        private risksPurposesService: RisksPurposesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.risksPurposesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'risksPurposesListModification',
                content: 'Deleted an risksPurposes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-risks-purposes-delete-popup',
    template: ''
})
export class RisksPurposesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private risksPurposesPopupService: RisksPurposesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.risksPurposesPopupService
                .open(RisksPurposesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

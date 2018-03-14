import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HighCommercialRisk } from './high-commercial-risk.model';
import { HighCommercialRiskPopupService } from './high-commercial-risk-popup.service';
import { HighCommercialRiskService } from './high-commercial-risk.service';

@Component({
    selector: 'jhi-high-commercial-risk-delete-dialog',
    templateUrl: './high-commercial-risk-delete-dialog.component.html'
})
export class HighCommercialRiskDeleteDialogComponent {

    highCommercialRisk: HighCommercialRisk;

    constructor(
        private highCommercialRiskService: HighCommercialRiskService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.highCommercialRiskService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'highCommercialRiskListModification',
                content: 'Deleted an highCommercialRisk'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-high-commercial-risk-delete-popup',
    template: ''
})
export class HighCommercialRiskDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private highCommercialRiskPopupService: HighCommercialRiskPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.highCommercialRiskPopupService
                .open(HighCommercialRiskDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

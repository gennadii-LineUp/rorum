import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {HighRisk} from './high-risk.model';
import {HighRiskPopupService} from './high-risk-popup.service';
import {HighRiskService} from './high-risk.service';

@Component({
    selector: 'jhi-high-risk-delete-dialog',
    templateUrl: './high-risk-delete-dialog.component.html'
})
export class HighRiskDeleteDialogComponent {

    highRisk: HighRisk;

    constructor(
        private highRiskService: HighRiskService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.highRiskService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'highRiskListModification',
                content: 'Deleted an highRisk'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-high-risk-delete-popup',
    template: ''
})
export class HighRiskDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private highRiskPopupService: HighRiskPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.highRiskPopupService
                .open(HighRiskDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

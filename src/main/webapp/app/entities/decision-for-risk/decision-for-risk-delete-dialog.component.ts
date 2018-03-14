import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {DecisionForRisk} from './decision-for-risk.model';
import {DecisionForRiskPopupService} from './decision-for-risk-popup.service';
import {DecisionForRiskService} from './decision-for-risk.service';

@Component({
    selector: 'jhi-decision-for-risk-delete-dialog',
    templateUrl: './decision-for-risk-delete-dialog.component.html'
})
export class DecisionForRiskDeleteDialogComponent {

    decisionForRisk: DecisionForRisk;

    constructor(
        private decisionForRiskService: DecisionForRiskService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.decisionForRiskService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'decisionForRiskListModification',
                content: 'Deleted an decisionForRisk'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-decision-for-risk-delete-popup',
    template: ''
})
export class DecisionForRiskDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private decisionForRiskPopupService: DecisionForRiskPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.decisionForRiskPopupService
                .open(DecisionForRiskDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

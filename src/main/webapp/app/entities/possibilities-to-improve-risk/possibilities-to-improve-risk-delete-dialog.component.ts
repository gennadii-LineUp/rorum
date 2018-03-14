import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {PossibilitiesToImproveRisk} from './possibilities-to-improve-risk.model';
import {PossibilitiesToImproveRiskPopupService} from './possibilities-to-improve-risk-popup.service';
import {PossibilitiesToImproveRiskService} from './possibilities-to-improve-risk.service';

@Component({
    selector: 'jhi-possibilities-to-improve-risk-delete-dialog',
    templateUrl: './possibilities-to-improve-risk-delete-dialog.component.html'
})
export class PossibilitiesToImproveRiskDeleteDialogComponent {

    possibilitiesToImproveRisk: PossibilitiesToImproveRisk;

    constructor(
        private possibilitiesToImproveRiskService: PossibilitiesToImproveRiskService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.possibilitiesToImproveRiskService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'possibilitiesToImproveRiskListModification',
                content: 'Deleted an possibilitiesToImproveRisk'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-possibilities-to-improve-risk-delete-popup',
    template: ''
})
export class PossibilitiesToImproveRiskDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private possibilitiesToImproveRiskPopupService: PossibilitiesToImproveRiskPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.possibilitiesToImproveRiskPopupService
                .open(PossibilitiesToImproveRiskDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

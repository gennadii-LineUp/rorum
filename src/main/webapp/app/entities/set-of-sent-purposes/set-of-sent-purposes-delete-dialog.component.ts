import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {SetOfSentPurposes} from './set-of-sent-purposes.model';
import {SetOfSentPurposesPopupService} from './set-of-sent-purposes-popup.service';
import {SetOfSentPurposesService} from './set-of-sent-purposes.service';

@Component({
    selector: 'jhi-set-of-sent-purposes-delete-dialog',
    templateUrl: './set-of-sent-purposes-delete-dialog.component.html'
})
export class SetOfSentPurposesDeleteDialogComponent implements OnInit {

    setOfSentPurposes: SetOfSentPurposes;
    set_purposes_id: number;
    order_id: number;
    parent_type: string;

    constructor(
        private setOfSentPurposesService: SetOfSentPurposesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.parent_type = this.setOfSentPurposesService.getParentType();
        this.order_id = +this.setOfSentPurposesService.getOrderId();
        this.set_purposes_id = +this.setOfSentPurposesService.getSetPurposesId();
        console.log('service: order  ' + this.order_id + ', parent_type ' + this.parent_type + ', set ' + this.set_purposes_id);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        console.log(id);
        this.setOfSentPurposesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'setOfSentPurposesListModification',
                content: 'Deleted an setOfSentPurposes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-set-of-sent-purposes-delete-popup',
    template: ''
})
export class SetOfSentPurposesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private setOfSentPurposesPopupService: SetOfSentPurposesPopupService,
        private setOfSentPurposesService: SetOfSentPurposesService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.setOfSentPurposesPopupService
                .open(SetOfSentPurposesDeleteDialogComponent as Component, params[':id_set_purpose']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {SetOfSentPurposes} from './set-of-sent-purposes.model';
import {SetOfSentPurposesPopupService} from './set-of-sent-purposes-popup.service';
import {SetOfSentPurposesService} from './set-of-sent-purposes.service';
import {Orders, OrdersService} from '../orders';
import {ResponseWrapper, User, UserService} from '../../shared';
import {GlossaryOfPurposes, GlossaryOfPurposesService} from '../glossary-of-purposes';
import 'rxjs/add/operator/takeWhile';

@Component({
    selector: 'jhi-set-of-sent-purposes-dialog',
    templateUrl: './set-of-sent-purposes-dialog.component.html'
})
export class SetOfSentPurposesDialogComponent implements OnInit, OnDestroy {
    setOfSentPurposes: SetOfSentPurposes;
    isSaving: boolean;
    alive = true;
    orders: Orders[];
    users: User[];
    set_purposes_id: number;
    order_id: number;
    parent_type: string;
    ids = [];
    statusOfSending: string;
    // private subscription: Subscription;

    glossaryofpurposes: GlossaryOfPurposes[];
    creationDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private route: ActivatedRoute,
        private router: Router,
        private jhiAlertService: JhiAlertService,
        private setOfSentPurposesService: SetOfSentPurposesService,
        private ordersService: OrdersService,
        private userService: UserService,
        private glossaryOfPurposesService: GlossaryOfPurposesService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        // this.subscription = this.route.params.subscribe((params) => {
        //     console.log(params['parent_type']);
        //     console.log((+params['id_set_purpose']));

            this.parent_type = this.setOfSentPurposesService.getParentType();
            this.order_id = this.setOfSentPurposesService.getOrderId();
            this.set_purposes_id = this.setOfSentPurposesService.getSetPurposesId();
            this.ids = this.setOfSentPurposesService.getIds();
            this.statusOfSending = this.setOfSentPurposesService.getStatusOfSending();
            console.log(this.statusOfSending);
            console.log('service: order  ' + this.order_id + ', parent_type ' + this.parent_type + ', set ' + this.set_purposes_id);
        // });

        this.isSaving = false;
        this.ordersService.query()
            .subscribe((res: ResponseWrapper) => { this.orders = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.glossaryOfPurposesService.query()
            .subscribe((res: ResponseWrapper) => { this.glossaryofpurposes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    ngOnDestroy() {
        this.alive = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    rejectSetOfPurposes() {
        this.setOfSentPurposesService.rejectSetOfPurposesByAdmin(this.order_id, this.set_purposes_id, this.setOfSentPurposes.notation, this.ids)
            .takeWhile(() => this.alive)
            .subscribe(
                (res: any) => {
                    console.log(res.status);
                    if (+res.status === 200) {
                        this.setOfSentPurposesService.setModalRejectCheckingStatus(true);
                        this.clear();
                    }
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
    }

    rejectPlan() {
        this.setOfSentPurposesService.rejectPlanByAdmin(this.order_id, this.set_purposes_id, this.setOfSentPurposes.notation)
            .takeWhile(() => this.alive)
            .subscribe(
                (res: any) => {
                    console.log(res.status);
                    if (+res.status === 200) {
                        this.setOfSentPurposesService.setModalRejectCheckingStatus(true);
                        this.clear();
                    }
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
    }

    save() {
        this.isSaving = true;
        if (this.setOfSentPurposes.id !== undefined) {
            this.subscribeToSaveResponse(
                this.setOfSentPurposesService.update(this.setOfSentPurposes));
        } else {
            this.subscribeToSaveResponse(
                this.setOfSentPurposesService.create(this.setOfSentPurposes));
        }
    }

    private subscribeToSaveResponse(result: Observable<SetOfSentPurposes>) {
        result.subscribe((res: SetOfSentPurposes) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SetOfSentPurposes) {
        this.eventManager.broadcast({ name: 'setOfSentPurposesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackOrdersById(index: number, item: Orders) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackGlossaryOfPurposesById(index: number, item: GlossaryOfPurposes) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-set-of-sent-purposes-popup',
    template: ''
})
export class SetOfSentPurposesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private setOfSentPurposesPopupService: SetOfSentPurposesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.setOfSentPurposesPopupService
                    .open(SetOfSentPurposesDialogComponent as Component, params['id']);
            } else {
                this.setOfSentPurposesPopupService
                    .open(SetOfSentPurposesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

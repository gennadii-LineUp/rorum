import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {DecisionForRisk} from './decision-for-risk.model';
import {DecisionForRiskService} from './decision-for-risk.service';

@Injectable()
export class DecisionForRiskPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private decisionForRiskService: DecisionForRiskService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.decisionForRiskService.find(id).subscribe((decisionForRisk) => {
                    this.ngbModalRef = this.decisionForRiskModalRef(component, decisionForRisk);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.decisionForRiskModalRef(component, new DecisionForRisk());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    decisionForRiskModalRef(component: Component, decisionForRisk: DecisionForRisk): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.decisionForRisk = decisionForRisk;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}

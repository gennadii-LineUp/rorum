import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {PossibilitiesToImproveRisk} from './possibilities-to-improve-risk.model';
import {PossibilitiesToImproveRiskService} from './possibilities-to-improve-risk.service';

@Injectable()
export class PossibilitiesToImproveRiskPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private possibilitiesToImproveRiskService: PossibilitiesToImproveRiskService

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
                this.possibilitiesToImproveRiskService.find(id).subscribe((possibilitiesToImproveRisk) => {
                    this.ngbModalRef = this.possibilitiesToImproveRiskModalRef(component, possibilitiesToImproveRisk);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.possibilitiesToImproveRiskModalRef(component, new PossibilitiesToImproveRisk());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    possibilitiesToImproveRiskModalRef(component: Component, possibilitiesToImproveRisk: PossibilitiesToImproveRisk): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.possibilitiesToImproveRisk = possibilitiesToImproveRisk;
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

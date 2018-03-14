import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {RisksPurposes} from './risks-purposes.model';
import {RisksPurposesService} from './risks-purposes.service';

@Injectable()
export class RisksPurposesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private risksPurposesService: RisksPurposesService

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
                this.risksPurposesService.find(id).subscribe((risksPurposes) => {
                    if (risksPurposes.creationDate) {
                        risksPurposes.creationDate = {
                            year: risksPurposes.creationDate.getFullYear(),
                            month: risksPurposes.creationDate.getMonth() + 1,
                            day: risksPurposes.creationDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.risksPurposesModalRef(component, risksPurposes);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.risksPurposesModalRef(component, new RisksPurposes());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    risksPurposesModalRef(component: Component, risksPurposes: RisksPurposes): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.risksPurposes = risksPurposes;
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

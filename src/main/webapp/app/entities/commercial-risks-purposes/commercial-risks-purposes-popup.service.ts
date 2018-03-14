import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {CommercialRisksPurposes} from './commercial-risks-purposes.model';
import {CommercialRisksPurposesService} from './commercial-risks-purposes.service';

@Injectable()
export class CommercialRisksPurposesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private commercialRisksPurposesService: CommercialRisksPurposesService

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
                this.commercialRisksPurposesService.find(id).subscribe((commercialRisksPurposes) => {
                    if (commercialRisksPurposes.creationDate) {
                        commercialRisksPurposes.creationDate = {
                            year: commercialRisksPurposes.creationDate.getFullYear(),
                            month: commercialRisksPurposes.creationDate.getMonth() + 1,
                            day: commercialRisksPurposes.creationDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.commercialRisksPurposesModalRef(component, commercialRisksPurposes);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.commercialRisksPurposesModalRef(component, new CommercialRisksPurposes());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    commercialRisksPurposesModalRef(component: Component, commercialRisksPurposes: CommercialRisksPurposes): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.commercialRisksPurposes = commercialRisksPurposes;
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

import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HighCommercialRisk } from './high-commercial-risk.model';
import { HighCommercialRiskService } from './high-commercial-risk.service';

@Injectable()
export class HighCommercialRiskPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private highCommercialRiskService: HighCommercialRiskService

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
                this.highCommercialRiskService.find(id).subscribe((highCommercialRisk) => {
                    this.ngbModalRef = this.highCommercialRiskModalRef(component, highCommercialRisk);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.highCommercialRiskModalRef(component, new HighCommercialRisk());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    highCommercialRiskModalRef(component: Component, highCommercialRisk: HighCommercialRisk): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.highCommercialRisk = highCommercialRisk;
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

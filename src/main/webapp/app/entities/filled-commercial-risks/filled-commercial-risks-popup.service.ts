import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {FilledCommercialRisks} from './filled-commercial-risks.model';
import {FilledCommercialRisksService} from './filled-commercial-risks.service';

@Injectable()
export class FilledCommercialRisksPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private filledCommercialRisksService: FilledCommercialRisksService

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
                this.filledCommercialRisksService.find(id).subscribe((filledCommercialRisks) => {
                    this.ngbModalRef = this.filledCommercialRisksModalRef(component, filledCommercialRisks);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.filledCommercialRisksModalRef(component, new FilledCommercialRisks());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    filledCommercialRisksModalRef(component: Component, filledCommercialRisks: FilledCommercialRisks): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.filledCommercialRisks = filledCommercialRisks;
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

import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {FilledMeasureUnits} from './filled-measure-units.model';
import {FilledMeasureUnitsService} from './filled-measure-units.service';

@Injectable()
export class FilledMeasureUnitsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private filledMeasureUnitsService: FilledMeasureUnitsService

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
                this.filledMeasureUnitsService.find(id).subscribe((filledMeasureUnits) => {
                    this.ngbModalRef = this.filledMeasureUnitsModalRef(component, filledMeasureUnits);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.filledMeasureUnitsModalRef(component, new FilledMeasureUnits());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    filledMeasureUnitsModalRef(component: Component, filledMeasureUnits: FilledMeasureUnits): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.filledMeasureUnits = filledMeasureUnits;
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

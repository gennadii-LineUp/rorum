import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {GlossaryOfMeasureUnits} from './glossary-of-measure-units.model';
import {GlossaryOfMeasureUnitsService} from './glossary-of-measure-units.service';

@Injectable()
export class GlossaryOfMeasureUnitsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private glossaryOfMeasureUnitsService: GlossaryOfMeasureUnitsService

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
                this.glossaryOfMeasureUnitsService.find(id).subscribe((glossaryOfMeasureUnitsDTO) => {
                    this.ngbModalRef = this.glossaryOfMeasureUnitsModalRef(component, glossaryOfMeasureUnitsDTO);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.glossaryOfMeasureUnitsModalRef(component, new GlossaryOfMeasureUnits());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    glossaryOfMeasureUnitsModalRef(component: Component, glossaryOfMeasureUnitsDTO: GlossaryOfMeasureUnits): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.glossaryOfMeasureUnitsDTO = glossaryOfMeasureUnitsDTO;
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

import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {MeasureUnitsPurposes} from './measure-units-purposes.model';
import {MeasureUnitsPurposesService} from './measure-units-purposes.service';

@Injectable()
export class MeasureUnitsPurposesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private measureUnitsPurposesService: MeasureUnitsPurposesService

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
                this.measureUnitsPurposesService.find(id).subscribe((measureUnitsPurposes) => {
                    if (measureUnitsPurposes.creationDate) {
                        measureUnitsPurposes.creationDate = {
                            year: measureUnitsPurposes.creationDate.getFullYear(),
                            month: measureUnitsPurposes.creationDate.getMonth() + 1,
                            day: measureUnitsPurposes.creationDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.measureUnitsPurposesModalRef(component, measureUnitsPurposes);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.measureUnitsPurposesModalRef(component, new MeasureUnitsPurposes());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    measureUnitsPurposesModalRef(component: Component, measureUnitsPurposes: MeasureUnitsPurposes): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.measureUnitsPurposes = measureUnitsPurposes;
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

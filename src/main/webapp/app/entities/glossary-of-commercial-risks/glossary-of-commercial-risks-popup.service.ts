import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {GlossaryOfCommercialRisks} from './glossary-of-commercial-risks.model';
import {GlossaryOfCommercialRisksService} from './glossary-of-commercial-risks.service';

@Injectable()
export class GlossaryOfCommercialRisksPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private glossaryOfCommercialRisksService: GlossaryOfCommercialRisksService

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
                this.glossaryOfCommercialRisksService.find(id).subscribe((glossaryOfCommercialRisksDTO) => {
                    if (glossaryOfCommercialRisksDTO.completionDate) {
                        glossaryOfCommercialRisksDTO.completionDate = {
                            year: glossaryOfCommercialRisksDTO.completionDate.getFullYear(),
                            month: glossaryOfCommercialRisksDTO.completionDate.getMonth() + 1,
                            day: glossaryOfCommercialRisksDTO.completionDate.getDate()
                        };
                    }
                    if (glossaryOfCommercialRisksDTO.creationDate) {
                        glossaryOfCommercialRisksDTO.creationDate = {
                            year: glossaryOfCommercialRisksDTO.creationDate.getFullYear(),
                            month: glossaryOfCommercialRisksDTO.creationDate.getMonth() + 1,
                            day: glossaryOfCommercialRisksDTO.creationDate.getDate()
                        };
                    }
                    if (glossaryOfCommercialRisksDTO.importantTo) {
                        glossaryOfCommercialRisksDTO.importantTo = {
                            year: glossaryOfCommercialRisksDTO.importantTo.getFullYear(),
                            month: glossaryOfCommercialRisksDTO.importantTo.getMonth() + 1,
                            day: glossaryOfCommercialRisksDTO.importantTo.getDate()
                        };
                    }
                    this.ngbModalRef = this.glossaryOfCommercialRisksModalRef(component, glossaryOfCommercialRisksDTO);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.glossaryOfCommercialRisksModalRef(component, new GlossaryOfCommercialRisks());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    glossaryOfCommercialRisksModalRef(component: Component, glossaryOfCommercialRisksDTO: GlossaryOfCommercialRisks): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.glossaryOfCommercialRisksDTO = glossaryOfCommercialRisksDTO;
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

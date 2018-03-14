import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {GlossaryOfRisks} from './glossary-of-risks.model';
import {GlossaryOfRisksService} from './glossary-of-risks.service';

@Injectable()
export class GlossaryOfRisksPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private glossaryOfRisksService: GlossaryOfRisksService

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
                this.glossaryOfRisksService.find(id).subscribe((glossaryOfRisksDTO) => {
                    if (glossaryOfRisksDTO.completionDate) {
                        glossaryOfRisksDTO.completionDate = {
                            year: glossaryOfRisksDTO.completionDate.getFullYear(),
                            month: glossaryOfRisksDTO.completionDate.getMonth() + 1,
                            day: glossaryOfRisksDTO.completionDate.getDate()
                        };
                    }
                    if (glossaryOfRisksDTO.creationDate) {
                        glossaryOfRisksDTO.creationDate = {
                            year: glossaryOfRisksDTO.creationDate.getFullYear(),
                            month: glossaryOfRisksDTO.creationDate.getMonth() + 1,
                            day: glossaryOfRisksDTO.creationDate.getDate()
                        };
                    }
                    if (glossaryOfRisksDTO.importantTo) {
                        glossaryOfRisksDTO.importantTo = {
                            year: glossaryOfRisksDTO.importantTo.getFullYear(),
                            month: glossaryOfRisksDTO.importantTo.getMonth() + 1,
                            day: glossaryOfRisksDTO.importantTo.getDate()
                        };
                    }
                    this.ngbModalRef = this.glossaryOfRisksModalRef(component, glossaryOfRisksDTO);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.glossaryOfRisksModalRef(component, new GlossaryOfRisks());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    glossaryOfRisksModalRef(component: Component, glossaryOfRisksDTO: GlossaryOfRisks): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.glossaryOfRisksDTO = glossaryOfRisksDTO;
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

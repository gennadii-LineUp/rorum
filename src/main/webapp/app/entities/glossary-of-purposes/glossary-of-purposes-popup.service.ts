import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {GlossaryOfPurposes} from './glossary-of-purposes.model';
import {GlossaryOfPurposesService} from './glossary-of-purposes.service';

@Injectable()
export class GlossaryOfPurposesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private glossaryOfPurposesService: GlossaryOfPurposesService

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
                this.glossaryOfPurposesService.find(id).subscribe((glossaryOfRisksService) => {
                    this.ngbModalRef = this.glossaryOfPurposesModalRef(component, glossaryOfRisksService);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.glossaryOfPurposesModalRef(component, new GlossaryOfPurposes());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    glossaryOfPurposesModalRef(component: Component, glossaryOfRisksService: GlossaryOfPurposes): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.glossaryOfRisksService = glossaryOfRisksService;
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

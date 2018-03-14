import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {GlossaryOfProcesses} from './glossary-of-processes.model';
import {GlossaryOfProcessesService} from './glossary-of-processes.service';

@Injectable()
export class GlossaryOfProcessesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private glossaryOfProcessesService: GlossaryOfProcessesService

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
                this.glossaryOfProcessesService.find(id).subscribe((glossaryOfProcesses) => {
                    if (glossaryOfProcesses.importantTo) {
                        glossaryOfProcesses.importantTo = {
                            year: glossaryOfProcesses.importantTo.getFullYear(),
                            month: glossaryOfProcesses.importantTo.getMonth() + 1,
                            day: glossaryOfProcesses.importantTo.getDate()
                        };
                    }
                    this.ngbModalRef = this.glossaryOfProcessesModalRef(component, glossaryOfProcesses);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.glossaryOfProcessesModalRef(component, new GlossaryOfProcesses());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    glossaryOfProcessesModalRef(component: Component, glossaryOfProcesses: GlossaryOfProcesses): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.glossaryOfProcesses = glossaryOfProcesses;
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

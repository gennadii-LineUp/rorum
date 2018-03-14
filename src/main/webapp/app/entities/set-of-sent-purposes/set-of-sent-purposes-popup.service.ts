import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {SetOfSentPurposes} from './set-of-sent-purposes.model';
import {SetOfSentPurposesService} from './set-of-sent-purposes.service';

@Injectable()
export class SetOfSentPurposesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private setOfSentPurposesService: SetOfSentPurposesService

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
                this.setOfSentPurposesService.find(id).subscribe((setOfSentPurposes) => {
                    if (setOfSentPurposes.creationDate) {
                        setOfSentPurposes.creationDate = {
                            year: setOfSentPurposes.creationDate.getFullYear(),
                            month: setOfSentPurposes.creationDate.getMonth() + 1,
                            day: setOfSentPurposes.creationDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.setOfSentPurposesModalRef(component, setOfSentPurposes);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.setOfSentPurposesModalRef(component, new SetOfSentPurposes());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    setOfSentPurposesModalRef(component: Component, setOfSentPurposes: SetOfSentPurposes): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.setOfSentPurposes = setOfSentPurposes;
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

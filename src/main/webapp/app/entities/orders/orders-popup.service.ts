import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {Orders} from './models/orders.model';
import {OrdersService} from './orders.service';

@Injectable()
export class OrdersPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private ordersService: OrdersService

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
                this.ordersService.find(id).subscribe((orders) => {
                    if (orders.startDate) {
                        orders.startDate = {
                            year: orders.startDate.getFullYear(),
                            month: orders.startDate.getMonth() + 1,
                            day: orders.startDate.getDate()
                        };
                    }
                    if (orders.firstReportingDate) {
                        orders.firstReportingDate = {
                            year: orders.firstReportingDate.getFullYear(),
                            month: orders.firstReportingDate.getMonth() + 1,
                            day: orders.firstReportingDate.getDate()
                        };
                    }
                    if (orders.secondReportingDate) {
                        orders.secondReportingDate = {
                            year: orders.secondReportingDate.getFullYear(),
                            month: orders.secondReportingDate.getMonth() + 1,
                            day: orders.secondReportingDate.getDate()
                        };
                    }
                    if (orders.thirdReportingDate) {
                        orders.thirdReportingDate = {
                            year: orders.thirdReportingDate.getFullYear(),
                            month: orders.thirdReportingDate.getMonth() + 1,
                            day: orders.thirdReportingDate.getDate()
                        };
                    }
                    if (orders.finalDate) {
                        orders.finalDate = {
                            year: orders.finalDate.getFullYear(),
                            month: orders.finalDate.getMonth() + 1,
                            day: orders.finalDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.ordersModalRef(component, orders);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.ordersModalRef(component, new Orders());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    ordersModalRef(component: Component, orders: Orders): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.orders = orders;
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

import { Component, OnInit, OnDestroy } from '@angular/core';
import { IncidentService } from './incident.service';
import {Orders} from "../orders";

@Component({
    selector: 'jhi-incident',
    templateUrl: './incident.component.html'
})
export class IncidentComponent implements OnInit, OnDestroy {
    orders: Orders[];

    constructor(
        private incidentService: IncidentService,
    ) {
    }

    loadAll() {
        this.incidentService.loadAllOrders().subscribe(
            (res) => {
                console.log(res);
                // this.test = res
                this.orders = res.json},
            (error) => {console.error(error)}
        );
        // console.log(this.test);
    }
    ngOnInit() {
        this.loadAll();
    }

    ngOnDestroy() {
    }

}

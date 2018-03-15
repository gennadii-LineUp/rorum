import {Component, OnDestroy, OnInit} from '@angular/core';
import {JhiAlertService, JhiParseLinks} from "ng-jhipster";
import {IncidentService} from "./incident.service";
import {ActivatedRoute} from "@angular/router";
import {Incident} from "./incident.model";
import {Orders} from "../orders";
import {Message} from "primeng/components/common/api";

@Component({
    selector: 'incident-order',
    templateUrl: './incident-order.component.html',
})
export class IncidentOrderComponent implements OnInit, OnDestroy {
    orderId: any;
    incidents: Incident[];
    predicate: string;
    reverse: any;
    currentSearch: any;
    msgs: Message[] = [];

    constructor(
        private activatedRoute: ActivatedRoute,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private incidentService: IncidentService,
    ) { }

    ngOnInit(): void {
        this.activatedRoute.params.subscribe(
            (param) => {
                this.orderId = param['orderId'];
                this.incidentService.getAllParentedOrSupervisoredCellsIncidents(this.orderId).subscribe(
                    (res) => {
                        this.incidents = res.json;
                        console.log(this.incidents)
                    },
                    (error) => {console.error('Problem getting Incidents: ' + error)},
                    () => {}
                );
            },
            (error) => {console.error("Nieprawidłowe przypisanie parametru - orderId")}
        );

    }

    ngOnDestroy(): void {
    }

    setSupervisor(incident: Incident) {
        this.incidentService.setSupervisor(incident).subscribe(() => {})
    }

    showWarn() {
        this.msgs = [];
        this.msgs.push({severity: 'warn', summary: 'Uwaga!', detail: 'Funkcjonalność nie została jeszcze przygotowana'});
    }

}

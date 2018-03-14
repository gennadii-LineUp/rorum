import {Component, OnDestroy, OnInit} from '@angular/core';
import {JhiAlertService, JhiParseLinks} from "ng-jhipster";
import {IncidentService} from "./incident.service";
import {ActivatedRoute} from "@angular/router";
import {SetOfSentPurposes} from "../set-of-sent-purposes";
import {Orders} from "../orders";
import {GlossaryOfPurposes} from "../glossary-of-purposes";
import {GlossaryOfRisks} from "../glossary-of-risks";
import {Message, SelectItem} from "primeng/primeng";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MessageService} from "primeng/components/common/messageservice";

@Component({
    selector: 'incident-user-order',
    templateUrl: './incident-user-order.component.html',
})
export class IncidentUserOrderComponent implements OnInit, OnDestroy {

    message: Message[];
    setOfSentPurposes: SetOfSentPurposes;
    test: any;
    order: Orders;
    orderId: any;
    statusOfSending: any;
    purposes: GlossaryOfPurposes[];
    risks: GlossaryOfRisks[];
    chosenRiskId: any;
    chosenPurposeId: any;
    setOfSentPurposesId: any;
    checked: boolean;
    incident: FormGroup;

    constructor(
        private activatedRoute: ActivatedRoute,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private incidentService: IncidentService,
        private messageService: MessageService
    ) { }

    ngOnInit(): void {
        this.checked = false;
        this.activatedRoute.params.subscribe(
            (param) => {
                this.orderId = param['orderId'];
                this.incidentService.loadSetOfSentPurposes(this.orderId).subscribe(
                    (res) => {
                        this.setOfSentPurposes = res.json;
                        this.assignProperties(this.setOfSentPurposes);
                    },
                    (error) => {console.error('Problem getting Order: ' + error)},
                    () => {}
                );
            },
            (error) => {console.error("Nieprawidłowe przypisanie parametru - orderId")}
        );

        this.incident = new FormGroup({
            description: new FormControl('', [Validators.required]),
            descriptionOfReaction: new FormControl('', [Validators.required]),
            descriptionOfPlannedActivities: new FormControl('', [Validators.required]),
            isCritical: new FormControl(false),
            glossaryOfPurposesId: new FormControl(),
            filledRisksId: new FormControl(),
            setOfSentPurposesId: new FormControl(),
            status: new FormControl('REPORTED')
        })

    }


    loadRisks(purposeId:number) {
        this.incidentService.loadRisksForSpecificPurpose(this.orderId, purposeId).subscribe(
            (res) => {
                this.risks = res.json;
            },
            (error) => {console.error(error)}
        );

    }
    assignProperties(plan: SetOfSentPurposes) {
        this.statusOfSending = plan.statusOfSending;
        this.purposes = plan.glossaryOfPurposes;
        this.setOfSentPurposesId = plan.id;
        this.incident.controls['setOfSentPurposesId'].setValue(plan.id);
    }

    ngOnDestroy(): void {
    }

    // createIncident() {
    //     console.log("I create an incident");
    //     console.log("PurposeId: " + this.chosenPurposeId + ", SetOfSentId: " + this.setOfSentPurposesId + ", RyzykoId: " + this.chosenRiskId);
    // }

    getRisks(id: number) {
        console.log("get Risks, where p.id: " + this.incident.controls['glossaryOfPurposesId'].value);
        this.risks = null;
        this.loadRisks(this.incident.controls['glossaryOfPurposesId'].value);
        this.clearChosenRisk();
    }

    clearChosenPurpose() {
        this.risks = null;
        this.incident.controls['glossaryOfPurposesId'].setValue(null);
    }
    clearChosenRisk() {
        this.incident.controls['filledRisksId'].setValue(null);
    }
}

import {Component, OnDestroy, OnInit} from '@angular/core';
import {JhiAlertService, JhiParseLinks} from "ng-jhipster";
import {IncidentService} from "../incident.service";
import {ActivatedRoute} from "@angular/router";
import {SetOfSentPurposes} from "../../set-of-sent-purposes";
import {Orders} from "../../orders";
import {GlossaryOfPurposes} from "../../glossary-of-purposes";
import {GlossaryOfRisks} from "../../glossary-of-risks";
import {Message} from "primeng/primeng";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MessageService} from "primeng/components/common/messageservice";
import {Incident} from "../incident.model";

@Component({
    selector: 'incident-user-order',
    templateUrl: './incident-user-order.component.html',
})
export class IncidentUserOrderComponent implements OnInit, OnDestroy {

    message: Message[] = [];
    setOfSentPurposes: SetOfSentPurposes;
    order: Orders;
    orderId: any;
    statusOfSending: any;
    purposes: GlossaryOfPurposes[];
    risks: GlossaryOfRisks[];
    setOfSentPurposesOriginId: any;
    incident: FormGroup;
    wasSubmitted: boolean;

    constructor(private activatedRoute: ActivatedRoute,
                private parseLinks: JhiParseLinks,
                private jhiAlertService: JhiAlertService,
                private incidentService: IncidentService,
                private messageService: MessageService) {
    }

    ngOnInit(): void {
        // this.checked = false;
        this.activatedRoute.params.subscribe(
            (param) => {
                this.orderId = param['orderId'];
                this.incidentService.loadSetOfSentPurposes(this.orderId).subscribe(
                    (res) => {
                        this.setOfSentPurposes = res.json;
                        this.assignProperties(res.json);
                    },
                    (error) => {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Błąd pobierania danych',
                            detail: 'Aby przejść do tej zakładki należy mieć wypełniony i zatwierdzony plan. Wystąpił błąd przy pobieraniu danych: ' + error
                        });
                    },
                    () => {
                    }
                );
            },
            (error) => {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Błąd pobierania danych',
                    detail: 'Wystąpił błąd przy pobieraniu danych: ' + error
                });
            }
        );
        this.createForm();
    }

    createForm() {
        this.incident = new FormGroup({
            description: new FormControl('', [Validators.required]),
            descriptionOfReaction: new FormControl('', [Validators.required]),
            descriptionOfPlannedActivities: new FormControl('', [Validators.required]),
            isCritical: new FormControl(false),
            glossaryOfPurposesId: new FormControl(0),
            glossaryOfRisksId: new FormControl(0),
            setOfSentPurposesId: new FormControl(),
            filledRisksId: new FormControl(0),
            status: new FormControl('REPORTED')
        })
    }

    loadRisks(purposeId: number) {
        this.incidentService.loadRisksForSpecificPurpose(this.orderId, purposeId).subscribe(
            (res) => {
                this.risks = res.json;
            },
            (error) => {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Błąd pobierania danych',
                    detail: 'Wystąpił błąd przy pobieraniu listy ryzyk: ' + error
                });
            }
        );
    }

    resetToPostNewIncident() {
        this.incident.enable();
        this.incident.reset();
        this.createForm();
        this.incident.controls['setOfSentPurposesId'].setValue(this.setOfSentPurposesOriginId);
        this.wasSubmitted = false;
    }

    assignProperties(plan: SetOfSentPurposes) {
        this.statusOfSending = plan.statusOfSending;
        this.purposes = plan.glossaryOfPurposes;
        this.setOfSentPurposesOriginId = plan.id;
        this.incident.controls['setOfSentPurposesId'].setValue(plan.id);
    }

    ngOnDestroy(): void {
    }

    getRisks() {
        this.risks = null;
        this.loadRisks(this.incident.controls['glossaryOfPurposesId'].value);
        this.clearChosenRisk();
    }

    getFilledRisks() {
        this.incidentService.loadFilledRiskForIncident(this.incident.controls['glossaryOfRisksId'].value, this.incident.controls['setOfSentPurposesId'].value).subscribe(
            (res) => {
                this.incident.controls['filledRisksId'].setValue(res.json().id);
            }
        );
    }

    onSubmit({value, valid}: { value: Incident, valid: boolean }) {
        this.incident.disable();
        if (valid) {
            this.incidentService.createForIncidentForm(value).subscribe((res) => {
                    if (res.status === 201) {
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Incydent dodany!',
                            detail: 'Incydent o id ' + res.json().id + ' został dodany poprawnie.'
                        });
                        this.wasSubmitted = true;
                    } else {
                        this.messageService.add({
                            severity: 'warning',
                            summary: 'Błąd przy dodawaniu incydentu',
                            detail: 'Skontaktuj się z administratorem'
                        })
                    }
                    ;
                },
                (error) => {
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Błąd przy dodawaniu incydentu',
                        detail: 'Skontaktuj się z administratorem, błąd: ' + error
                    });
                    this.incident.enable();
                }
            );
        } else {
            if(this.wasSubmitted === true) {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Błąd przy dodawaniu incydentu',
                    detail: 'Nie można ponownie wysłać tego samego incydentu!'
                });
            } else {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Błąd przy dodawaniu incydentu',
                    detail: 'Nie wszystkie pola są wypełnione poprawnie!'
                });
                this.wasSubmitted = false;
            }
            this.wasSubmitted === false ? this.incident.enable() : this.incident.disable();
        }
    }

    clearChosenPurpose() {
        this.risks = null;
        this.incident.controls['glossaryOfPurposesId'].setValue(null);
        this.incident.controls['glossaryOfRisksId'].setValue(null);
    }

    clearChosenRisk() {
        this.incident.controls['glossaryOfRisksId'].setValue(null);
    }
}

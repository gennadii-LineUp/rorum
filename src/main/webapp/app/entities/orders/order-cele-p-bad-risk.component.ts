import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {RisksDataClass} from './models/risks-data.model';
import {CommercialRisksDataClass} from './models/commercial-risks-data.model';
import {ResponseWrapper} from '../../shared';
import {OrdersService} from './orders.service';
import 'rxjs/add/operator/takeWhile';
import {JhiAlertService} from 'ng-jhipster';
import {CheckboxesFromSelectDataModel} from './models/checkboxes-from-select-data.model';
import {HighRiskDataModel} from './models/high-risk-data.model';
import {SelectDataModel} from './models/select-data.model';

@Component({
    selector: 'jhi-popup-bad-risk',
    templateUrl: './order-cele-p-bad-risk.component.html',
    styleUrls: ['./order-cele-p-bad-risk.component.css'],
    providers: [OrdersService]
})
export class OrderCelePopupBadRiskComponent implements OnInit, OnDestroy {
    _editable: number;
    initialDatafilled = false;
    textVisibility = false;
    decisions: Array<SelectDataModel>;
    decision_selected: string;
    riskOwner = {
        email: '',
        firstName: '',
        imageUrl: '',
        lastName: '',
        organisationStructureId: 0
    };
    riskOrg = {
        name: '',
        specifyingCells: ''
    };
    radio_values = [
        { value: true, description: '1' },
        { value: false, description: '2' }
    ];
    select_costs_values = [
        {value: undefined, display: ''},
        {value: 'COSTS_MORE_BENEFITS', display: 'KOSZTY > KORZYŚCI'},
        {value: 'COSTS_ARE_EQUALED_TO_BENEFITS', display: 'KOSZTY = KORZYŚCI'},
        {value: 'COSTS_LESS_BENEFITS', display: 'KOSZTY < KORZYŚCI'}
    ];
    csy_mozns_q1 = 'Wycofać się z realizacji celu?';
    csy_mozns_q2 = 'Przesunąć realizację do czasu pozyskania lub zabezpieczenia środków (osobowych, rzeczowych, finansowych)?';
    csy_mozns_q3 = 'Ograniczyć zakres realizacji celu?';
    rejectToAccomplishPurpose = true;
    postponePurposeAccomplishment = false;
    restrictRangeOfPurposeAccomplishment = false;
    purposesToCheck = [];
    probabilities = Array<CheckboxesFromSelectDataModel>(0);
    powerOfInfluences = Array<CheckboxesFromSelectDataModel>(0);
    newPossibility = '';
    edit_possibility: any;
    _substantiationForAnalyze: any;
    _i: number;
    _disadledMode: boolean;
    _dataIncomeCR: CommercialRisksDataClass;
    _dataIncomeR: RisksDataClass;
    _startDate = {year: undefined, month: undefined, day: undefined};
    _endDate = {year: undefined, month: undefined, day: undefined};
    @Input() title: string;
    @Input() purpose: string;
    @Input() highRisk: HighRiskDataModel;
    @Input() set dataIncomeR(data: RisksDataClass) {

        console.log(this.highRisk);
        if (this.highRisk && this.highRisk.projectedTermDeployStart) {
                    const startD = this.highRisk.projectedTermDeployStart.split('-');
                    this._startDate.year = +startD['0'];
                    this._startDate.month = +startD['1'];
                    this._startDate.day = +startD['2'];
        }
        if (this.highRisk && this.highRisk.projectedTermDeployFinish) {
                    const endD = this.highRisk.projectedTermDeployFinish.split('-');
                    this._endDate.year = +endD['0'];
                    this._endDate.month = +endD['1'];
                    this._endDate.day = +endD['2'];
        }
        console.log(data);
        this._dataIncomeR = data;
        if (this.highRisk && data.powerOfInfluence) {
            this.highRisk.powerOfInfluenceToReach = data.strengthOfControlFunctionPowerOfInfluence;
            this.highRisk.probabilityToReach = data.strengthOfControlFunctionProbability;
            // this._substantiationForAnalyze = (dataDTO) ? dataDTO.substantiationForAnalyze : '';
        }
        console.log(this.highRisk);
    };
    @Input() set dataIncomeCR(data: CommercialRisksDataClass) {
        console.log(data);
        this._dataIncomeCR = data;
        if (this.highRisk && data.powerOfInfluence) {
            this.highRisk.powerOfInfluenceToReach = data.strengthOfControlFunctionPowerOfInfluence;
            this.highRisk.probabilityToReach = data.strengthOfControlFunctionProbability;
        }
        console.log(this.highRisk);
    };
    @Input() set checkboxesProbabilities(data: Array<CheckboxesFromSelectDataModel>) {
        console.log(data);
        if (this._dataIncomeR && this._dataIncomeR.probabilities.length) {
            this._dataIncomeR.probabilities.forEach((probability) => {
                data.forEach((obj) => {
                    if (obj.id === probability) { this.probabilities.push(obj); }
                });
            });
        }
        if (this._dataIncomeCR && this._dataIncomeCR.probabilities.length) {
            this._dataIncomeCR.probabilities.forEach((probability) => {
                data.forEach((obj) => {
                        if (obj.id === probability) { this.probabilities.push(obj); }
                });
            });
        }
        // console.log(this.probabilities);
    };
    @Input() set checkboxesPowerOfInfluences(data: Array<CheckboxesFromSelectDataModel>) {
        if (this._dataIncomeR && this._dataIncomeR.powerOfInfluences.length) {
            this._dataIncomeR.powerOfInfluences.forEach((power) => {
                data.forEach((obj) => {
                    if (obj.id === power) { this.powerOfInfluences.push(obj); }
                });
            });
        }
        if (this._dataIncomeCR && this._dataIncomeCR.powerOfInfluences.length) {
            this._dataIncomeCR.powerOfInfluences.forEach((power) => {
                data.forEach((obj) => {
                    if (obj.id === power) { this.powerOfInfluences.push(obj); }
                });
            });
        }
    };
    @Input() set disadledMode(disadledMode: boolean) {
        this._disadledMode = disadledMode;
        console.log(this._disadledMode);
    }
    @Output() onClosePopup = new EventEmitter<boolean>();
    @Output() dataOutput = new EventEmitter<HighRiskDataModel>();
    @Output() highRiskChange = new EventEmitter<HighRiskDataModel>();
    alive = true;

    constructor(public ordersService: OrdersService,
                private jhiAlertService: JhiAlertService) {}

    ngOnInit() {
        this.getLocalAdminFunction();
        this.getDecisionsForRisksFunction();
    }

    ngOnDestroy() {this.alive = false; }

    getLocalAdminFunction() {
        this.ordersService.getLocalAdmin()
            .takeWhile(() => this.alive)
            .subscribe(
                (res: any) => {
                    console.log(res);
                    this.riskOwner = {
                        email: (res.email) ? res.email : undefined,
                        firstName: (res.firstName) ? res.firstName : undefined,
                        imageUrl: (res.imageUrl) ? res.imageUrl : undefined,
                        lastName: (res.lastName) ? res.lastName : undefined,
                        organisationStructureId: (res.organisationStructureId) ? res.organisationStructureId : undefined
                    };
                    if (this.riskOwner.organisationStructureId) {
                        this.ordersService.getOrganisationStructure(this.riskOwner.organisationStructureId)
                            .takeWhile(() => this.alive)
                            .subscribe(
                                (_res: any) => {
                                    console.log(_res);
                                    this.riskOrg = {
                                        name: (_res.name) ? _res.name : '',
                                        specifyingCells: (_res.specifyingCells) ? _res.specifyingCells : ''
                                    };
                                },
                                (_res: ResponseWrapper) => this.onError(_res.json)
                            );
                    }
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
    }

    getDecisionsForRisksFunction() {
        this.ordersService.getDecisionForRisks()
            .takeWhile(() => this.alive)
            .subscribe(
                (res: any) => {
                    console.log(res);
                    this.decisions = res;
                    if (this._disadledMode && this.decisions.length && this.highRisk.decisionForRiskId) {
                        this.decision_selected = (this.decisions.filter((d) => d.id === this.highRisk.decisionForRiskId))['0'].name;
                    }
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    convertDateFromDatepicker(data: any) {
        let dateObj: any;
        switch (data) {
            case 'startDate':
                dateObj = this._startDate;
                this.highRisk.projectedTermDeployStart = '' + dateObj.year + '-'
                    + ((('' + dateObj.month).length > 1) ? dateObj.month : ('0' + dateObj.month)) + '-'
                    + ((('' + dateObj.day).length > 1) ? dateObj.day : ('0' + dateObj.day));
                break;
            case 'endDate':
                dateObj = this._endDate;
                this.highRisk.projectedTermDeployFinish = '' + dateObj.year + '-'
                    + ((('' + dateObj.month).length > 1) ? dateObj.month : ('0' + dateObj.month)) + '-'
                    + ((('' + dateObj.day).length > 1) ? dateObj.day : ('0' + dateObj.day));
                break;
            default:
                console.log('we have problems!');
                break;
        }
        this.sendHighRisk();
    }
    public sendHighRisk() {
        console.log(this.highRisk);
        // this.dataOutput.emit(this.highRisk);
        this.highRiskChange.emit(this.highRisk);
    }

    public editPossibility(i: number, possibility: string) {
        this.edit_possibility = possibility;
        this._i = i;
    }
    public savePossibility(i: number) {
        this.highRisk.possibilitiesToImproveRisks.splice(i, 1, this.edit_possibility);
        this._i = undefined;
        this.sendHighRisk();
    }
    public removePossibility(i: number) {
        this.highRisk.possibilitiesToImproveRisks.splice(i, 1);
    }
    public addPossibility() {
        if (!this.highRisk.possibilitiesToImproveRisks || !this.highRisk.possibilitiesToImproveRisks.length) {
            this.highRisk.possibilitiesToImproveRisks = [];
        }
        this.highRisk.possibilitiesToImproveRisks.push(this.newPossibility);
        this.newPossibility = '';
    }

    public saveSubstantiationForAnalyze() {
        this.highRisk.substantiationForAnalyze = this._substantiationForAnalyze;
        this.sendHighRisk();
    }

    closePopup() {
        // console.log('close popup');
        this.onClosePopup.emit(true);
    }

}

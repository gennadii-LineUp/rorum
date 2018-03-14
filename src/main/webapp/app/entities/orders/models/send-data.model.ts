import {CommercialRisksDataClass} from './commercial-risks-data.model';

export class SendDataClass {
    id: number;
    ids: Array<number>;
    statusOfSending: string;
    notation: string;
    commercialRisksPurposesDTO: {setOfSentPurposesId: number, filledCommercialRisksDTOS: Array<CommercialRisksDataClass>};
    measureUnitsPurposesDTOS: Array<any>;
    risksPurposesDTOS: Array<any>;

    constructor(id: number,
                ids: Array<number>,
                statusOfSending: string,
                notation: string,
                commercialRisksPurposesDTO: {setOfSentPurposesId: number, filledCommercialRisksDTOS: Array<any>},
                measureUnitsPurposesDTOS: Array<any>,
                risksPurposesDTOS: Array<any>) {

        this.id = id;
        this.ids = ids;
        this.statusOfSending = statusOfSending;
        this.notation = notation;
        this.commercialRisksPurposesDTO = commercialRisksPurposesDTO;
        this.measureUnitsPurposesDTOS = measureUnitsPurposesDTOS;
        this.risksPurposesDTOS = risksPurposesDTOS;
    }
}

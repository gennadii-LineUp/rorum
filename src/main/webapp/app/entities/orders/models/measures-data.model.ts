export class MeasuresDataClass {
    measureUnitsPurposesId: number;
    baseValue: number;
    actualValue: number;
    finalValue: number;
    purposeAccomplishmentStatus: string;
    glossaryOfMeasureUnitsDTO: {id: number, name: string, unitsOfMeasurement: string};
    saved: boolean;
    costOfPurposeRealisation: number;

    constructor(measureUnitsPurposesId: number,
                baseValue: number,
                actualValue: number,
                finalValue: number,
                purposeAccomplishmentStatus: string,
                glossaryOfMeasureUnitsDTO: {id: number, name: string, unitsOfMeasurement: string},
                saved: boolean,
                costOfPurposeRealisation: number) {

        this.measureUnitsPurposesId = measureUnitsPurposesId;
        this.baseValue = baseValue;
        this.actualValue = actualValue;
        this.finalValue = finalValue;
        this.purposeAccomplishmentStatus = purposeAccomplishmentStatus;
        this.glossaryOfMeasureUnitsDTO = glossaryOfMeasureUnitsDTO;
        this.saved = saved;
        this.costOfPurposeRealisation = costOfPurposeRealisation;
    }
}

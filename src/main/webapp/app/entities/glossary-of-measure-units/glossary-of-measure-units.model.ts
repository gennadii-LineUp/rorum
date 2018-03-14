import {BaseEntity} from './../../shared';

export const enum UnitsOfMeasurement {
    'ZL',
    'OS',
    'KM',
    'SZT',
    'L',
    'M2',
    'KG'
}

export const enum FrequencyOfGatheringData {
    'DAY',
    ' WEEK',
    ' MONTH',
    ' YEAR'
}

export class GlossaryOfMeasureUnits implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public unitsOfMeasurement?: UnitsOfMeasurement,
        public frequencyOfGatheringData?: FrequencyOfGatheringData,
        public isChecked?: boolean,
        public glossaryOfRisksService?: BaseEntity,
        public organisationStructure?: BaseEntity,
    ) {
        this.isChecked = false;
    }
}

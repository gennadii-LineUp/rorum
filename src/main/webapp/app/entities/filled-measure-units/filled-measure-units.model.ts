import {BaseEntity} from './../../shared';

export const enum OptionOfMeasureUnits {
    'M',
    ' KM',
    ' MM'
}

export class FilledMeasureUnits implements BaseEntity {
    constructor(
        public id?: number,
        public baseValue?: number,
        public option?: OptionOfMeasureUnits,
        public finalValue?: number,
        public glossaryOfRisksService?: BaseEntity,
        public measureUnitsPurposes?: BaseEntity,
        public glossaryOfMeasureUnitsDTO?: BaseEntity,
    ) {
    }
}

import {BaseEntity} from './../../shared';

export class MeasureUnitsPurposes implements BaseEntity {
    constructor(
        public id?: number,
        public creationDate?: any,
        public setOfSentPurposes?: BaseEntity,
        public filledMeasureUnits?: BaseEntity[],
    ) {
    }
}

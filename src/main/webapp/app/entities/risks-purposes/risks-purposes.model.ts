import {BaseEntity} from './../../shared';

export class RisksPurposes implements BaseEntity {
    constructor(
        public id?: number,
        public creationDate?: any,
        public setOfSentPurposes?: BaseEntity,
        public filledRisks?: BaseEntity[],
    ) {
    }
}

import { BaseEntity } from './../../shared';

export class Incident implements BaseEntity {
    constructor(
        public id?: number,
        public description?: string,
        public descriptionOfReaction?: string,
        public descriptionOfPlannedActivities?: string,
        public isCritical?: boolean,
        public setOfSentPurposes?: BaseEntity,
        public glossaryOfPurposes?: BaseEntity,
        public filledRisks?: BaseEntity,
        public filledCommercialRisks?: BaseEntity,
    ) {
        this.isCritical = false;
    }
}

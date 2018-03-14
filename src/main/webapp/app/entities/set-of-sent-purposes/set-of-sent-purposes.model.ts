import {BaseEntity, User} from './../../shared';

export const enum StatusOfSending {
    'CONFIRMED',
    ' REJECT',
    ' UNCHECKED',
    ' SAVED'
}

export class SetOfSentPurposes implements BaseEntity {
    constructor(
        public id?: number,
        public statusOfSending?: StatusOfSending,
        public isLastVersion?: boolean,
        public creationDate?: any,
        public notation?: string,
        public orders?: BaseEntity,
        public user?: User,
        public glossaryOfRisksService?: BaseEntity[],
        public measureUnitsPurposes?: BaseEntity[],
        public risksPurposes?: BaseEntity[],
        public glossaryOfPurposes?: BaseEntity[],
    ) {
        this.isLastVersion = false;
    }
}

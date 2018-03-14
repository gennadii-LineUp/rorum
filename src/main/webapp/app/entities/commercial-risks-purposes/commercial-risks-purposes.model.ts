import {BaseEntity} from './../../shared';

export const enum StatusOfSending {
    'CONFIRMED',
    '  REJECT',
    '  UNCHECKED',
    '  SAVED'
}

export class CommercialRisksPurposes implements BaseEntity {
    constructor(
        public id?: number,
        public statusOfSending?: StatusOfSending,
        public creationDate?: any,
        public notation?: string,
        public setOfSentPurposes?: BaseEntity,
    ) {
    }
}

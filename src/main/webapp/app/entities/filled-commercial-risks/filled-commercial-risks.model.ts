import {BaseEntity} from './../../shared';

export class FilledCommercialRisks implements BaseEntity {
    constructor(
        public id?: number,
        public probability?: number,
        public powerOfInfluence?: number,
        public calculatedValue?: number,
        public glossaryOfCommercialRisksDTO?: BaseEntity,
        public commercialRisksPurposes?: BaseEntity,
    ) {
    }
}

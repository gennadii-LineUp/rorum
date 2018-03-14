import {BaseEntity} from './../../shared';

export class FilledRisks implements BaseEntity {
    constructor(
        public id?: number,
        public probability?: number,
        public powerOfInfluence?: number,
        public calculatedValue?: number,
        public glossaryOfRisksService?: BaseEntity,
        public risksPurposes?: BaseEntity,
        public glossaryOfRisksDTO?: BaseEntity,
    ) {
    }
}

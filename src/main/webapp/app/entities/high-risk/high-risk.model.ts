import {BaseEntity} from './../../shared';

export const enum AnalyzeOfRisk {
    'COSTS_MORE_BENEFITS',
    ' COSTS_ARE_EQUALED_TO_BENEFITS',
    ' COSTS_LESS_BENEFITS'
}

export class HighRisk implements BaseEntity {
    constructor(
        public id?: number,
        public rejectToAccomplishPurpose?: boolean,
        public postponePurposeAccomplishment?: boolean,
        public restrictRangeOfPurposeAccomplishment?: boolean,
        public costOfListedPossibilities?: number,
        public projectedTermDeployStart?: number,
        public projectedTermDeployFinish?: number,
        public probabilityToReach?: number,
        public powerOfInfluenceToReach?: number,
        public analyze?: AnalyzeOfRisk,
        public substantiationForAnalyze?: string,
        public commentToHighRiskProcedure?: string,
        public filledRisks?: BaseEntity,
        public decisionForRisk?: BaseEntity,
        public possibilitiesToImproveRisks?: BaseEntity[],
    ) {
        this.rejectToAccomplishPurpose = false;
        this.postponePurposeAccomplishment = false;
        this.restrictRangeOfPurposeAccomplishment = false;
    }
}

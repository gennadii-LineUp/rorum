export class HighRiskDataModel {
    analyze: string;
    commentToHighRiskProcedure: string;
    costOfListedPossibilities: number;
    decisionForRiskId: number;
    // filledRiskId: number;
    possibilitiesToImproveRisks: Array<string>;
    postponePurposeAccomplishment: boolean;
    powerOfInfluenceToReach: number;
    probabilityToReach: number;
    projectedTermDeployFinish: string;
    projectedTermDeployStart: string;
    rejectToAccomplishPurpose: boolean;
    restrictRangeOfPurposeAccomplishment: boolean;
    substantiationForAnalyze: string;

    constructor(analyze: string,
    commentToHighRiskProcedure: string,
    costOfListedPossibilities: number,
    decisionForRiskId: number,
    // filledRiskId: number,
    possibilitiesToImproveRisks: Array<string>,
    postponePurposeAccomplishment: boolean,
    powerOfInfluenceToReach: number,
    probabilityToReach: number,
    projectedTermDeployFinish: string,
    projectedTermDeployStart: string,
    rejectToAccomplishPurpose: boolean,
    restrictRangeOfPurposeAccomplishment: boolean,
    substantiationForAnalyze: string) {

        this.analyze = analyze;
        this.commentToHighRiskProcedure = commentToHighRiskProcedure;
        this.costOfListedPossibilities = costOfListedPossibilities;
        this.decisionForRiskId = decisionForRiskId;
        // this.filledRiskId = filledRiskId;
        this.possibilitiesToImproveRisks = possibilitiesToImproveRisks;
        this.postponePurposeAccomplishment = postponePurposeAccomplishment;
        this.powerOfInfluenceToReach = powerOfInfluenceToReach;
        this.probabilityToReach = probabilityToReach;
        this.projectedTermDeployFinish = projectedTermDeployFinish;
        this.projectedTermDeployStart = projectedTermDeployStart;
        this.rejectToAccomplishPurpose = rejectToAccomplishPurpose;
        this.restrictRangeOfPurposeAccomplishment = restrictRangeOfPurposeAccomplishment;
        this.substantiationForAnalyze = substantiationForAnalyze;
    }
}

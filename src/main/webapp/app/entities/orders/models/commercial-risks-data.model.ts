import {HighRiskDataModel} from './high-risk-data.model';

export class CommercialRisksDataClass {
    commercialRiskPurposesId: number;
    powerOfInfluence: number;
    probability: number;
    reactionOnRisks: string;
    notationConcernRisk: string;
    glossaryOfCommercialRisksDTO: any;
    strengthOfControlFunctionProbability: number;
    probabilities: Array<number>;
    strengthOfControlFunctionPowerOfInfluence: number;
    powerOfInfluences: Array<number>;
    saved: boolean;
    responsiblePerson: string;
    stateForDay: any;
    highRiskDTO: HighRiskDataModel;

    constructor(commercialRiskPurposesId: number,
                powerOfInfluence: number,
                probability: number,
                reactionOnRisks: string,
                notationConcernRisk: string,
                glossaryOfCommercialRisksDTO: any,
                strengthOfControlFunctionProbability: number,
                probabilities: Array<number>,
                strengthOfControlFunctionPowerOfInfluence: number,
                powerOfInfluences: Array<number>,
                saved: boolean,
                responsiblePerson: string,
                stateForDay: any,
                highRiskDTO: HighRiskDataModel) {

        this.commercialRiskPurposesId = commercialRiskPurposesId;
        this.powerOfInfluence = powerOfInfluence;
        this.probability = probability;
        this.reactionOnRisks = reactionOnRisks;
        this.notationConcernRisk = notationConcernRisk;
        this.glossaryOfCommercialRisksDTO = glossaryOfCommercialRisksDTO;
        this.strengthOfControlFunctionProbability = strengthOfControlFunctionProbability;
        this.probabilities = probabilities;
        this.strengthOfControlFunctionPowerOfInfluence = strengthOfControlFunctionPowerOfInfluence;
        this.powerOfInfluences = powerOfInfluences;
        this.saved = saved;
        this.responsiblePerson = responsiblePerson;
        this.stateForDay = stateForDay;
        this.highRiskDTO = highRiskDTO;
    }
}

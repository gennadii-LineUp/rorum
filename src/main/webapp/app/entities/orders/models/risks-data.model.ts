import {HighRiskDataModel} from './high-risk-data.model';

export class RisksDataClass {
    risksPurposesId: number;
    powerOfInfluence: number;
    probability: number;
    reactionOnRisks: string;
    notationConcernRisk: string;
    glossaryOfRisksDTO: {id: number, name: string};
    strengthOfControlFunctionProbability: number;
    probabilities: Array<number>;
    strengthOfControlFunctionPowerOfInfluence: number;
    powerOfInfluences: Array<number>;
    saved: boolean;
    responsiblePerson: string;
    stateForDay: any;
    highRiskDTO: HighRiskDataModel;

    constructor(risksPurposesId: number,
                powerOfInfluence: number,
                probability: number,
                reactionOnRisks: string,
                notationConcernRisk: string,
                glossaryOfRisksDTO: {id: number, name: string},
                strengthOfControlFunctionProbability: number,
                probabilities: Array<number>,
                strengthOfControlFunctionPowerOfInfluence: number,
                powerOfInfluences: Array<number>,
                saved: boolean,
                responsiblePerson: string,
                stateForDay: any,
                highRiskDTO: HighRiskDataModel) {

        this.risksPurposesId = risksPurposesId;
        this.powerOfInfluence = powerOfInfluence;
        this.probability = probability;
        this.reactionOnRisks = reactionOnRisks;
        this.notationConcernRisk = notationConcernRisk;
        this.glossaryOfRisksDTO = glossaryOfRisksDTO;
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

import { BaseEntity } from './../../shared';
import {User} from "../../shared/user/user.model";

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
        public statusOfIncident?: string,
        public supervisedBy?: number,
        public user?: User
    ) {}
}

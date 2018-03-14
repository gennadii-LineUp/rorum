import {BaseEntity, User} from './../../shared';

export class GlossaryOfCommercialRisks implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public completionDate?: any,
        public creationDate?: any,
        public importantTo?: any,
        public isChecked?: boolean,
        public user?: User,
        public organisationStructure?: BaseEntity,
    ) {
        this.isChecked = false;
    }
}

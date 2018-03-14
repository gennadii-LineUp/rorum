import {BaseEntity} from './../../shared';

export class DecisionForRisk implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}

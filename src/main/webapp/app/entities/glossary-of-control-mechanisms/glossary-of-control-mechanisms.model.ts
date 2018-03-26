import {BaseEntity} from './../../shared';

export class GlossaryOfControlMechanisms implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public parentId?: any,
        public possibleToMark?: boolean
    ) {
    }
}

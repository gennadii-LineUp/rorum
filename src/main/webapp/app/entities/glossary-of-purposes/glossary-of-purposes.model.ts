import {BaseEntity} from './../../shared';

export const enum AssignmentToCell {
    'JEDNOSTKAWYKONAWCZA',
    'URZADDZIELNICY',
    'BIURO'
}

export class GlossaryOfPurposes implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public parentId?: number,
        public sumUp?: boolean,
        public assignmentToCell?: AssignmentToCell,
        public isChecked?: boolean,
        public glossaryOfProcesses?: BaseEntity,
        public glossaryOfMeasureUnitsDTO?: BaseEntity[],
        public glossaryOfRisksDTO?: BaseEntity[],
        public organisationStructure?: BaseEntity,
    ) {
        this.sumUp = false;
        this.isChecked = false;
    }
}

import {BaseEntity} from './../../shared';

export const enum SpecifyingCells {
    'BIURO',
    '  URZAD',
    '  JEDNOSTKAORGANIZACYJNA',
    '  JEDNOSTKAWYKONAWCZA',
    '  WYDZIAL',
    ' WOJEWODZTWO',
    ' MIASTO',
    ' URZADDZIELNICY',
    ' URZEDYDZIELNIC'
}

export class OrganisationStructure implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public parentId?: number,
        public supervisoryUnitId?: number,
        public statusId?: boolean,
        public specifyingCells?: SpecifyingCells,
        public glossaryOfProcesses?: BaseEntity,
        public glossaryOfCommercialRisksDTO?: BaseEntity[],
    ) {
        this.statusId = false;
    }
}

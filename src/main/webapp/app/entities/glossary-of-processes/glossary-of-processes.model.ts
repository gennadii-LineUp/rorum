import {BaseEntity} from './../../shared';

export class GlossaryOfProcesses implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public importantTo?: any,
        public organisationStructures?: BaseEntity[],
        public glossaryOfRisksService?: BaseEntity[],
    ) {
    }
}

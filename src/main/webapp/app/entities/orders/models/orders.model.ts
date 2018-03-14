import {BaseEntity} from '../../../shared/index';

export class Orders implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public financingYear?: number,
        public startDate?: any,
        public firstReportingDate?: any,
        public secondReportingDate?: any,
        public thirdReportingDate?: any,
        public finalDate?: any,
    ) {
    }
}

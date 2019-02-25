import { ITimeTable } from 'app/shared/model//time-table.model';
import { ICourse } from 'app/shared/model//course.model';

export interface ILab {
    id?: number;
    labName?: string;
    labType?: string;
    labVolume?: number;
    timeTable?: ITimeTable;
    courses?: ICourse[];
}

export class Lab implements ILab {
    constructor(
        public id?: number,
        public labName?: string,
        public labType?: string,
        public labVolume?: number,
        public timeTable?: ITimeTable,
        public courses?: ICourse[]
    ) {}
}

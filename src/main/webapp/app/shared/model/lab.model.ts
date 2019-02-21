import { Moment } from 'moment';
import { ICourse } from 'app/shared/model//course.model';

export interface ILab {
    id?: number;
    labName?: string;
    labType?: string;
    labVolume?: number;
    labTime?: Moment;
    courses?: ICourse[];
}

export class Lab implements ILab {
    constructor(
        public id?: number,
        public labName?: string,
        public labType?: string,
        public labVolume?: number,
        public labTime?: Moment,
        public courses?: ICourse[]
    ) {}
}

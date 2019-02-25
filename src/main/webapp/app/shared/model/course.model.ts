import { ITeacher } from 'app/shared/model//teacher.model';
import { ITimeTable } from 'app/shared/model//time-table.model';
import { ILab } from 'app/shared/model//lab.model';

export interface ICourse {
    id?: number;
    courseName?: string;
    courseType?: string;
    timeTable?: string;
    teacher?: ITeacher;
    timeTables?: ITimeTable[];
    labs?: ILab[];
}

export class Course implements ICourse {
    constructor(
        public id?: number,
        public courseName?: string,
        public courseType?: string,
        public timeTable?: string,
        public teacher?: ITeacher,
        public timeTables?: ITimeTable[],
        public labs?: ILab[]
    ) {}
}
